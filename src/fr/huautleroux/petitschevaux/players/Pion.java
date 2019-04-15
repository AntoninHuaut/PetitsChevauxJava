package fr.huautleroux.petitschevaux.players;

import fr.huautleroux.petitschevaux.enums.Couleur;

public class Pion {
	private String id;
	private Couleur coul;
	
	Pion(String s, Couleur c){
		this.id = s;
		this.coul = c;
	}
	
	public String getID() {
		return this.id;
	}
	
	public Couleur getCouleur() {
		return this.coul;
		
	}
}
