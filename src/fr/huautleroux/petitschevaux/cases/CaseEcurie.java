package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEcurie extends CaseColoree {

	public CaseEcurie(int ident,Couleur couleur) {
		super(ident,couleur);
	}
	
	@Override
	public boolean peutPasser(Pion pion) {
		return false;
	}

	@Override
	public boolean peutSArreter(Pion pion) {
		return false;
	}

}
