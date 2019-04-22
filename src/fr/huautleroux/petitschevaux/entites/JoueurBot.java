package fr.huautleroux.petitschevaux.entites;

import java.util.List;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.cases.CaseChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
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
		List<JoueurAction> actionDispo = getActionDisponible(de, plateau, false);
		actionDispo.remove(JoueurAction.SAUVEGARDER);
		actionDispo.remove(JoueurAction.RIEN_FAIRE);
		
		if (actionDispo.isEmpty())
			return JoueurAction.RIEN_FAIRE;
		
		if (actionDispo.size() == 1)
			return actionDispo.get(0);
		
		return hasPionQuiPeutManger(de, plateau) ? JoueurAction.DEPLACER_CHEVAL : JoueurAction.SORTIR_CHEVAL;
	}
	
	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) {
		System.out.println("Bot " + getNom() + " a fait un " + de);
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
		Pion pionDeplaceable = null;
		int index = -1;
		
		for (Pion pion : getChevaux()) {
			int localIndex;
			Case caseActuelle = pion.getCaseActuelle();
			
			if (caseActuelle instanceof CaseChemin) {
				localIndex = plateau.getChemin().indexOf(caseActuelle);
				
				if (localIndex > index) {
					// TODO
					// Check si déplaceable (fonction externe dans plateau)
					
					pionDeplaceable = pion;
				}
			}
			
			else if (caseActuelle instanceof CaseEchelle) {
				int indexEchelle = plateau.getEchelles().get(getCouleur().ordinal()).indexOf(caseActuelle);
				localIndex = plateau.getChemin().size() + indexEchelle;
				
				if (localIndex > index) {
					int numeroCaseEchelle = indexEchelle + 1;
					
					if (de != numeroCaseEchelle + 1) // Pour atteindre la case n+1 de l'échelle en étant à l'échelle n, il faut faire un tirage de dé de n+1
						continue;
					
					// TODO
					// Check si déplaceable (fonction externe dans plateau)
					
					index = localIndex;
					pionDeplaceable = pion;
				}
			}	
		}
		
		return pionDeplaceable;
	}
	
	private Pion getPionQuiPeutManger(int de, Plateau plateau) {
		
		for (Pion pion : getChevaux()) {
			//Case caseActuelle = pion.getCaseActuelle();
			Case caseCible = pion.getCaseCible(plateau, de);
			
			boolean hasPionEnemy = !caseCible.getChevaux().stream().filter(enemy -> !enemy.getCouleur().equals(getCouleur())).collect(Collectors.toList()).isEmpty();
			
			 /* 
			 * Vérifier si le pion peut se déplacer de la caseActuelle à la caseCible
			 */
			
			if(hasPionEnemy)
				return pion;
		}
		
		return null;
	}
}
