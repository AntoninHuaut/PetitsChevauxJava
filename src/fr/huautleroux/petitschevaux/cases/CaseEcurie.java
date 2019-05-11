package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEcurie extends CaseColoree {

	public CaseEcurie(Couleur couleur, int numero) {
		super(couleur, numero);
	}
	
	@Override
	public boolean peutPasser(Pion pion) {
		return true;
	}

	@Override
	public boolean peutSArreter(Pion pion, int de) {
		return true;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " " + super.toString();
	}

}
