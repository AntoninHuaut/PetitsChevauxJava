package fr.huautleroux.petitschevaux.cases.abstracts;

import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class CaseColoree extends Case {
	
	private final Couleur couleur;
	
	public CaseColoree(Couleur couleur, int numero) {
		super(numero);
		this.couleur = couleur;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	@Override
	public String toString() {
		return couleur + super.toString();
	}
}
