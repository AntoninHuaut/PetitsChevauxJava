package fr.huautleroux.petitschevaux.players;

import java.util.ArrayList;

public abstract class Joueur {
	private String nom;
	
	Joueur(String name, Couleur coul){
	}
	
	public Case getCaseDeDepart() {
		
	}
	
	public void setCaseDeDepart(Case) {
		
	}
	
	public ArrayList<Pion> getChevaux(){
		
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String name) {
		this.nom = name;
	}
	
	public Couleur getCouleur() {
		
	}
	
	public abstract Pion choisirPion();
	
}
