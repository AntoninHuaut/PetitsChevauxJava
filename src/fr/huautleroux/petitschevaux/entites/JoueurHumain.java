package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	@Override
	public Pion choisirPion(int de, Plateau plateau) {
		System.out.println("Vous avez fait un " + de);

		if(hasToutPionEcurie(plateau)) { /* Tout les chevaux sont dans l'écurie */
			if(de == 6) 
				System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");
			else
				System.out.println("  Vous ne pouvez rien faire car tout vos chevaux sont dans l'écurie");
		}

		else { /* Il y a des pions sur le plateau */
			if(de == 6 && hasPionEcurie(plateau))
				System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");

			System.out.println("  Vous pouvez déplacer un cheval sur le plateau [2]");
		}

		return null;
	}

}
