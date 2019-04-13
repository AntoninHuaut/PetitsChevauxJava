package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Random;

public class Partie {
	private Random de;
	
	public Partie(){
		
		initialiserJoueurs();
		initialiserPlateau();
	}
	
	public void initialiserJoueurs(int nb){
		
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

