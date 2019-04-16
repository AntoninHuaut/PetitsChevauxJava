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
		/*Vérifier la position des Chevaux
		Si dé = 6 -> Sortir Cheval
		Si dé = 6 ET Chevaux déjà sur plateau = 2 Choix
		Si dé != 6 ET Chevaux déjà sur plateau = Choix
		Si dé != 6 ET Pas de chevaux sur le plateau = Pas de Choix
		Return Cheval à déplacer 
		 */
		if (de == 6){
			System.out.println("Vous pouvez sortir un cheval de l'écurie");
			/*Afficher la liste des chevaux dispos
			 if (chevsurplat)*/
		}
		else {
			System.out.println("Vous ne pouvez pas sortir de cheval");
		}
		
		return null;
	}

}
