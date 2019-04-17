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
		for (int i = 0; i < 4; i++)
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
		ArrayList<Pion> pions = new ArrayList<Pion>();

		for(Pion pion : getChevaux()) {
			ArrayList<Case> cases = new ArrayList<Case>();
			plateau.getEcuries().forEach(c -> cases.add(c));
			plateau.getChemin().forEach(c -> cases.add(c));
			plateau.getEchelles().forEach(get -> get.forEach(c -> cases.add(c)));
			
			boolean containsPion = false;
			
			for(int i = 0; i < cases.size() && !containsPion; i++)
				if(cases.get(i).getChevaux().contains(pion))
					containsPion = true;
			
			if(!containsPion)
				pions.add(pion);
		}

		return pions;
	}
}
