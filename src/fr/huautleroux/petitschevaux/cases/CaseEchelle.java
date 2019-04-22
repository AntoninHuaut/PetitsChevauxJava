package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEchelle extends CaseColoree {

	public CaseEchelle(Couleur couleur, int numero) {
		super(couleur, numero);
	}
	
	@Override
	public boolean peutPasser(Pion pion) {
		return getChevaux().isEmpty();
	}

	@Override
	public boolean peutSArreter(Pion pion, int de) {
		return getChevaux().isEmpty() && getNumero() + 1 == de;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " " + getCouleur() + toStringPions();
	}
}
