package fr.huautleroux.petitschevaux.players;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.enums.Couleur;

public abstract class Joueur {
	private String nom;
	private Couleur coul;
	
	Joueur(String name, Couleur c){
		this.nom = name;
		this.coul = c;
	}
	
	public Case getCaseDeDepart() {
		
	}
	
	public void setCaseDeDepart(Case) {
		
	}
	
	public ArrayList<Pion> getChevaux(){
		ArrayList<Pion> chevaux = new ArrayList<Pion>();
		
		
		return chevaux;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String name) {
		this.nom = name;
	}
	
	public Couleur getCouleur() {
		return this.coul;
	}
	
	public abstract Pion choisirPion();
	
}
