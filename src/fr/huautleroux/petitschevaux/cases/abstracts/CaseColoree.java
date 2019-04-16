package fr.huautleroux.petitschevaux.cases.abstracts;

import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class CaseColoree extends Case {
	
	private Couleur couleur;
	private int id;
	
	public CaseColoree(int ident, Couleur couleur) {
		this.id = ident;
		this.couleur = couleur;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public int getId() {
		return id;
	}
}
