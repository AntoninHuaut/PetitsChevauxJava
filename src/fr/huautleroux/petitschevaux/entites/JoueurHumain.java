package fr.huautleroux.petitschevaux.entites;

import java.util.List;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	/*
	 * 1) Il faut lui demander le numéro de l'action à effectuer
	 * 2) Lui afficher la liste des chevaux qui peuvent effectuer l'action demandée
	 * 3) Demander le numéro du cheval qui va effectuer l'action
	 */

	@Override
	public JoueurAction choixAction(int de, Plateau plateau) {
		System.out.println("Vous avez fait un " + de);
		System.out.println("  Vous pouvez ne rien faire [0]");

		if(hasToutPionEcurie(plateau)) { /* Tout les chevaux sont dans l'écurie */
			if(de == 6) 
				System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");
		}

		else { /* Il y a des pions sur le plateau */
			if(de == 6 && hasPionEcurie(plateau))
				System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");

			System.out.println("  Vous pouvez déplacer un cheval sur le plateau [2]");
		}

		System.out.println("  Vous pouvez sauvegarder [3]");
		System.out.print("\nVeuillez entrer le numéro de l'action que vous souhaitez effectuer : ");

		int choix;

		do {
			choix = Saisie.asInt();
			System.out.println("");
		} while(!estChoixValide(de, choix, plateau));

		return JoueurAction.values()[choix];
	};

	@Override
	public Pion choisirPion(int de, JoueurAction action) {
		List<Pion> pionsAction = getPionsParAction(action);

		System.out.println("Voici la liste de vos chevaux que vous pouvez " + action.getMessage());

		pionsAction.forEach(pion -> System.out.println("  • " + pion));
		System.out.print("\nVeuillez entrer le numéro du cheval que vous souhaitez " + action.getMessage() + " : ");

		boolean allowed = false;
		int numPion;

		do {
			numPion = Saisie.asInt() - 1;
			System.out.println("");

			for (Pion pion : pionsAction)
				if(pion.getId() == numPion)
					allowed = true;

		} while (!allowed);

		return pionsAction.get(numPion);
	}
}
