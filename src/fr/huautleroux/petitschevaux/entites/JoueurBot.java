package fr.huautleroux.petitschevaux.entites;

import java.util.List;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;

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
		
		// Empêche getPionDeplaceable() de retourner null dans choisirPion 
		if (choixAction.equals(JoueurAction.DEPLACER_CHEVAL) && getPionDeplaceable(de, plateau) == null)
			return JoueurAction.RIEN_FAIRE;
		
		return choixAction;
	}

	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) {
		Pion pionChoisi;

		if (action.equals(JoueurAction.SORTIR_CHEVAL))
			pionChoisi = getChevaux().stream().filter(pion -> pion.isDansEcurie()).findFirst().get();
		else {
			pionChoisi = getPionQuiPeutManger(de, plateau);

			if (pionChoisi == null)
				pionChoisi = getPionDeplaceable(de, plateau);
		}

		return pionChoisi;
	}

	private boolean hasPionQuiPeutManger(int de, Plateau plateau) {
		return getPionQuiPeutManger(de, plateau) != null;
	}

	private Pion getPionDeplaceable(int de, Plateau plateau) {
		for (Pion pion : getChevaux())
			if (pion.isDeplacementPossible(plateau, de))
				return pion;

		// Impossible que çela arrive
		return null;
	}

	private Pion getPionQuiPeutManger(int de, Plateau plateau) {
		for (Pion pion : getChevaux()) {
			if(!pion.isDeplacementPossible(plateau, de))
				continue;
			
			Case caseCible = pion.getCaseCible(plateau, de);
			boolean hasPionEnemy = !caseCible.getChevaux().stream().filter(enemy -> !enemy.getCouleur().equals(getCouleur())).collect(Collectors.toList()).isEmpty();

			if (hasPionEnemy)
				return pion;
		}

		return null;
	}
}
