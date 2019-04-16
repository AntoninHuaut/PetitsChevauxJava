package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.enums.Couleur;

public class Pion {
	
	private int id;
	private Couleur couleur;
	
	public Pion(int id, Couleur couleur) {
		this.id = id;
		this.couleur = couleur;
	}
	
	public int getId() {
		return id;
	}
	
	public Couleur getCouleur() {
		return couleur;
		
	}
}
