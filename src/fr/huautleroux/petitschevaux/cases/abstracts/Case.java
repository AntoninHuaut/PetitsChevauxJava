package fr.huautleroux.petitschevaux.cases.abstracts;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.entites.Pion;

public abstract class Case {
	
	private ArrayList<Pion> pions = new ArrayList<Pion>();
	
	public void ajouteCheval(Pion pion) {
		// TODO
	}

	public abstract boolean peutPasser(Pion pion);
	public abstract boolean peutSArreter(Pion pion);
	
	public ArrayList<Pion> getChevaux() {
		return pions;
	}
}