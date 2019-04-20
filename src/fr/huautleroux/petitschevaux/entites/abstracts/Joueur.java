package fr.huautleroux.petitschevaux.entites.abstracts;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class Joueur {

	private transient ArrayList<Pion> chevaux = new ArrayList<Pion>();
	private transient Case caseDepart = null;
	private String nom;
	private Couleur couleur;

	public Joueur(String name, Couleur couleur) {
		this.nom = name;
		this.couleur = couleur;
	}

	public abstract int choixAction(int de, Plateau plateau);
	public abstract Pion choisirPion(int de, int choix, Plateau plateau);

	public boolean hasToutPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) == 4;
	}

	public boolean hasPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) != 0;
	}

	public int getNombrePionEcurie(Plateau plateau) {
		return getPionEcurie(plateau).size();
	}

	public ArrayList<Pion> getPionEcurie(Plateau plateau) {
		return plateau.getEcuries().get(couleur.ordinal()).getChevaux();
	}
	
	public Case getCaseDeDepart() {
		return caseDepart;
	}

	public void setCaseDeDepart(Case caseDepart) {
		this.caseDepart = caseDepart;
	}

	public ArrayList<Pion> getChevaux() {
		return chevaux;
	}

	public void ajouterCheval(Pion pion) {
		this.chevaux.add(pion);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String name) {
		this.nom = name;
	}

	public Couleur getCouleur() {
		return couleur;
	}
	
	public void initialisationReference() {
		this.chevaux = new ArrayList<Pion>();
	}
}
