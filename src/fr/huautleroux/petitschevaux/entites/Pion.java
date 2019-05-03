package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Pion implements Comparable<Pion> {

	private transient Case caseActuelle = null;
	private int id;
	private Couleur couleur;

	public Pion(int id, Couleur couleur) {
		this.id = id;
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return "Pion n° " + (id + 1);
	}

	@Override
	public int compareTo(Pion pion) {
		return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(pion.getId()));
	}

	/**
	 * @return Permet de savoir si le cheval se trouve actuellement à l'écurie
	 **/
	public boolean isDansEcurie() {
		return caseActuelle instanceof CaseEcurie;
	}

	public Case getCaseActuelle() {
		return caseActuelle;
	}

	public void setCaseActuelle(Case caseActuelle) {
		this.caseActuelle = caseActuelle;
	}

	public int getId() {
		return id;
	}

	public Couleur getCouleur() {
		return couleur;
	}
}
