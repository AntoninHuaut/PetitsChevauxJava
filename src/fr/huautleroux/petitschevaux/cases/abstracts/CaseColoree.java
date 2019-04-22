package fr.huautleroux.petitschevaux.cases.abstracts;

import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class CaseColoree extends Case {
	
	private Couleur couleur;
	
	public CaseColoree(Couleur couleur) {
		this.couleur = couleur;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " " + couleur + toStringPions();
	}
}
