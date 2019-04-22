package fr.huautleroux.petitschevaux.cases.abstracts;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;

public abstract class Case {

	private List<Pion> pions = new ArrayList<Pion>();

	public void ajouteCheval(Plateau plateau, Pion pion) {
		Case caseActuel = pion.getCaseActuelle();
		
		if (caseActuel != null)
			caseActuel.retirerCheval(pion);
		
		pion.setCaseActuelle(this);
		pions.add(pion);
	}
	
	public void retirerCheval(Pion pion) {
		pions.remove(pion);
	}

	public abstract boolean peutPasser(Pion pion);
	public abstract boolean peutSArreter(Pion pion);

	public List<Pion> getChevaux() {
		return pions;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + toStringPions();
	}
	
	public String toStringPions() {
		String s = "";
		
		for (Pion pion : pions)
			s += "\n" + pion;
		
		return s;
	}
}