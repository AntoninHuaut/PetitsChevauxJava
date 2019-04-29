package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEchelle extends CaseColoree {

	public CaseEchelle(Couleur couleur, int numero) {
		super(couleur, numero);
	}
	
	public int getNumeroLocal() {
		return getNumero() % 6;
	}
	
	@Override
	public boolean peutPasser(Pion pion) {
		return getChevaux().isEmpty();
	}

	@Override
	public boolean peutSArreter(Pion pion, int de) {
		return getChevaux().isEmpty() && getNumeroLocal () + 1 == de;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " " + getCouleur() + toStringPions();
	}
}
