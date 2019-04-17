package fr.huautleroux.petitschevaux.entites.abstracts;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class Joueur {
	
	private ArrayList<Pion> chevaux = new ArrayList<Pion>();
	private Case caseDepart = null;
	private String nom;
	private Couleur couleur;
	
	public Joueur(String name, Couleur couleur) {
		this.nom = name;
		this.couleur = couleur;
		
		setChevaux();
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
	
	public void setChevaux() {
		for (int i=0; i<4;i++)
			this.chevaux.add(new Pion(i, getCouleur()));
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
