package fr.huautleroux.petitschevaux.cases.abstracts;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.entites.Pion;

public abstract class Case {

	private final int numero;
	private List<Pion> pions = new ArrayList<Pion>();
	
	public Case(int numero) {
		this.numero = numero;
	}

	/**
	 * Ajoute un cheval sur la case
	 * @param pion Pion à ajouter
	 */
	public void ajouteCheval(Pion pion) {
		pion.setCaseActuelle(this);
		pions.add(pion);
	}
	
	/**
	 * Retire un cheval sur la case
	 * @param pion Pion à retirer
	 */
	public void retirerCheval(Pion pion) {
		pions.remove(pion);
	}

	public abstract boolean peutPasser(Pion pion);
	public abstract boolean peutSArreter(Pion pion, int de);
	
	@Override
	public String toString() {
		return toStringPions();
	}
	
	/**
	 * Liste les pions sur la case
	 * @return Chaîne de caractère des pions sur la case
	 */
	private String toStringPions() {
		String s = "";
		
		for (Pion pion : pions)
			s += "\n" + pion;
		
		return s;
	}
	
	public List<Pion> getChevaux() {
		return pions;
	}
	
	public int getNumero() {
		return numero;
	}
}