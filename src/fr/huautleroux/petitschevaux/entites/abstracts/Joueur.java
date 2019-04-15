package fr.huautleroux.petitschevaux.entites.abstracts;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class Joueur {
	
	private ArrayList<Pion> chevaux;
	private Case caseDepart;
	private String nom;
	private Couleur couleur;
	
	public Joueur(String name, Couleur couleur) {
		this.chevaux = new ArrayList<Pion>();
		this.caseDepart = null;
		
		this.nom = name;
		this.couleur = couleur;
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
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String name) {
		this.nom = name;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public abstract Pion choisirPion(int de, Plateau plateau);
	
}
