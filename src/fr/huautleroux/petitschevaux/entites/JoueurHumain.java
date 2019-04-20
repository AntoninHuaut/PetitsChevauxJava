package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
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
	public int choixAction(int de, Plateau plateau) {
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

		return choix;
	};

	@Override
	public Pion choisirPion(int de, int choix, Plateau plateau) {
		if(choix == 1) {
			System.out.println("Voici la liste de vos chevaux que vous pouvez sortir de l'écurie");

			// TODO
		} else {
			System.out.println("Voici la liste de vos chevaux que vous pouvez déplacer sur le plateau");

			// TODO
		}

		return null;
	}

	private boolean estChoixValide(int de, int choix, Plateau plateau) {
		boolean choixValide = true;
		
		if (choix == 1 && !hasPionEcurie(plateau) // ucun cheval dans l'écurie
			|| choix == 1 && de != 6 // Il n'a pas le droit d'en sortir un de l'écurie
			|| choix == 2 && hasToutPionEcurie(plateau) // Aucun cheval sur le plateau
			|| choix < 0 || choix > 3) // Le numéro entré ne correspond à aucun choix
			choixValide = false;
		
		if(!choixValide)
			System.out.print("Votre choix n'est pas valide, reessayez : ");

		return choixValide;
	}
}
