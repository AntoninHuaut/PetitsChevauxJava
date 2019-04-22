package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;

public class CaseChemin extends Case {
	
	public CaseChemin() {}
	
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
		return getClass().getName() + toStringPions();
	}
}
