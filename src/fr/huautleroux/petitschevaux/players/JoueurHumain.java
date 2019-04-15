package fr.huautleroux.petitschevaux.players;

import fr.huautleroux.petitschevaux.enums.Couleur;

public class JoueurHumain extends Joueur{

	JoueurHumain(String n, Couleur c){
		super(n, c);
	}
	
	@Override
	public Pion choisirPion(int de) {
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
		
		
		
	};

	
}
