package fr.huautleroux.petitschevaux.entites;

import java.util.List;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.exceptions.AucunPionException;
import fr.huautleroux.petitschevaux.exceptions.PionFinParcoursException;

public class JoueurBot extends Joueur {

	private static int nbBot = 0;
	private static String[] noms = new String[] { "Alpha", "Bêta", "Gamma", "Delta" };

	public JoueurBot(Couleur couleur) {
		super(noms[nbBot >= noms.length ? noms.length - 1 : nbBot++], couleur);
	}

	@Override
	public JoueurAction choixAction(int de, Plateau plateau) {
		System.out.println("Bot " + getNom() + " a fait " + de + " au dé");
		List<JoueurAction> actionDispo = getActionDisponible(de, plateau, false);
		actionDispo.remove(JoueurAction.SAUVEGARDER);
		actionDispo.remove(JoueurAction.RIEN_FAIRE);

		if (actionDispo.isEmpty())
			return JoueurAction.RIEN_FAIRE;

		JoueurAction choixAction;

		if (actionDispo.size() == 1)
			choixAction = actionDispo.get(0);
		else {
			if(hasPionQuiPeutManger(de, plateau))
				choixAction = JoueurAction.DEPLACER_CHEVAL;
			else
				choixAction = JoueurAction.SORTIR_CHEVAL;
		}

		if(choixAction.equals(JoueurAction.DEPLACER_CHEVAL) && !hasPionDeplacable(de, plateau))
			return JoueurAction.RIEN_FAIRE;

		return choixAction;
	}

	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) throws AucunPionException {
		Pion pionChoisi;

		if (action.equals(JoueurAction.SORTIR_CHEVAL))
			pionChoisi = getChevaux().stream().filter(pion -> pion.isDansEcurie()).findFirst().get();
		else {
			try {
				pionChoisi = getPionQuiPeutManger(de, plateau);
			} catch (AucunPionException e) {
				pionChoisi = getPionDeplacable(de, plateau);
			}
		}

		return pionChoisi;
	}
	
	@Override
	public String toString() {
		return "Bot " + super.toString();
	}

	private boolean hasPionQuiPeutManger(int de, Plateau plateau) {
		try {
			getPionQuiPeutManger(de, plateau);
			return true;
		} catch (AucunPionException e) {
			return false;
		}
	}

	private boolean hasPionDeplacable(int de, Plateau plateau) {
		try {
			getPionDeplacable(de, plateau);
			return true;
		} catch (AucunPionException e) {
			return false;
		}
	}

	private Pion getPionDeplacable(int de, Plateau plateau) throws AucunPionException {
		for (Pion pion : getChevaux())
			if (pion.isDeplacementPossible(plateau, de)) 
				return pion;

		throw new AucunPionException("Aucun pion déplaçable trouvé");
	}

	private Pion getPionQuiPeutManger(int de, Plateau plateau) throws AucunPionException {
		for (Pion pion : getChevaux()) {
			if(!pion.isDeplacementPossible(plateau, de))
				continue;

			try {
				Case caseCible = pion.getCaseCible(plateau, de);
				
				boolean hasPionEnemy = !caseCible.getChevaux().stream().filter(enemy -> !enemy.getCouleur().equals(getCouleur())).collect(Collectors.toList()).isEmpty();

				if (hasPionEnemy)
					return pion;
			} catch (PionFinParcoursException e) {}
		}

		throw new AucunPionException("Aucun pion pouvant manger un autre pion trouvé");
	}
}
