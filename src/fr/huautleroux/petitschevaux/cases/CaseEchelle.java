package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEchelle extends CaseColoree {

	public CaseEchelle(Couleur couleur) {
		super(couleur);
	}
	
	@Override
	public boolean peutPasser(Pion pion) {
		return false;
	}

	@Override
	public boolean peutSArreter(Pion pion) {
		return false;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " " + getCouleur() + toStringPions();
	}
}
