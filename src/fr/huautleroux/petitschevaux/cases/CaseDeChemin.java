package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;

public class CaseDeChemin extends Case {
	
	public CaseDeChemin() {}
	
	@Override
	public boolean peutPasser(Pion pion) {
		return false;
	}

	@Override
	public boolean peutSArreter(Pion pion) {
		return false;
	}
}
