package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Random;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.players.Joueur;
import fr.huautleroux.petitschevaux.players.JoueurHumain;

public class Partie {
	private Random de;
	
	public Partie(){
		int nb;
		try {
			do {
				System.out.println("Combien de joueurs vont participer ?");
				nb = Saisie.getInteger();
			}while (nb>4 || nb<1);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Vous n'avez pas entré un chiffre");
		}
		initialiserJoueurs(nb);
		initialiserPlateau();
	}
	
	public void initialiserJoueurs(int nb){
		ArrayList<Joueur> players = new ArrayList<Joueur>();
		Couleur[] couleurs = Couleur.values();
		for(int i = 0; i<nb; i++) {
			String nom;
			System.out.println("Entrez votre pseudo");
			nom = Saisie.getString();
			players.add(new JoueurHumain(nom, couleurs[i]));
			
		}
	}
	
	public void initialiserPlateau() {
		
	}
	
	private int lancerDe(){
		
		int lanceN = this.de.nextInt((6-1)+1)+1;
		return lanceN;
	}
	
	public void jouerUnTour() {
		
	}
	
	public boolean estPartieTerminée() {
		return false;
	}
	
	public Joueur getJoueurCourrant() {
		
	}
	
	public void setJoueurCourrant(Joueur) {
		
	}
	
	public Plateau getPlateau() {
		
	}

	public ArrayList<Joueur> getJoueurs(){
		
	}
	
	private void mangerLesPions(Case) {
		
	}
	
}

