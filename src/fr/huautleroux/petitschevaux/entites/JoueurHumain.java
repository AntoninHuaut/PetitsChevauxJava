package fr.huautleroux.petitschevaux.entites;

import java.util.List;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.utils.Saisie;
import fr.huautleroux.petitschevaux.utils.Utils;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	@Override
	public JoueurAction choixAction(int de, Plateau plateau) {
		System.out.println("Vous avez fait " + de + " au dé");
		List<JoueurAction> actionDispo = getActionDisponible(de, plateau, true);

		System.out.print("\nVeuillez entrer le numéro de l'action que vous souhaitez effectuer : ");

		int choix;

		do {
			choix = Saisie.asInt();
			System.out.print("");
		} while(!estChoixValide(choix, actionDispo));

		return JoueurAction.values()[choix];
	};

	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) {
		List<Pion> pionsAction = getPionsParAction(action);

		System.out.println("\nVoici la liste de vos chevaux que vous pouvez " + action.getMessage());

		pionsAction.forEach(pion -> System.out.println("  • " + pion));
		System.out.print("\nVeuillez entrer le numéro du cheval que vous souhaitez " + action.getMessage() + " : ");

		int numPion;

		do {
			numPion = Saisie.asInt() - 1;
			System.out.print("");
		} while(!estPionValide(pionsAction, numPion));
		
		final int numPionFinal = numPion;
		return pionsAction.stream().filter(pionAction -> pionAction.getId() == numPionFinal).findFirst().get();
	}
	
	private boolean estPionValide(List<Pion> pionsAction, int numPion) {
		boolean estPionValide = false;
		
		for (Pion pion : pionsAction)
			if(pion.getId() == numPion)
				estPionValide = true;
		
		if (!estPionValide)
			System.out.print(Utils.RED_BRIGHT + "Le pion choisie est invalide" + Utils.RESET + ", veuillez en choisir un autre : ");
		
		return estPionValide;
	}

	private boolean estChoixValide(int choix, List<JoueurAction> actionDispo) {
		boolean estChoixValide = true;

		if (choix < 0 
				|| choix >= JoueurAction.values().length
				|| !actionDispo.contains(JoueurAction.values()[choix]))
			estChoixValide = false;

		if (!estChoixValide)
			System.out.print(Utils.RED_BRIGHT + "L'action choisie est invalide" + Utils.RESET + ", veuillez entrez une autre valeur : ");

		return estChoixValide;
	}
}
