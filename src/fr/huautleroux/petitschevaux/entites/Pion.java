package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.enums.Couleur;

public class Pion {
	
	private String id;
	private Couleur couleur;
	
	public Pion(String id, Couleur couleur) {
		this.id = id;
		this.couleur = couleur;
	}
	
	public String getId() {
		return id;
	}
	
	public Couleur getCouleur() {
		return couleur;
		
	}
}
