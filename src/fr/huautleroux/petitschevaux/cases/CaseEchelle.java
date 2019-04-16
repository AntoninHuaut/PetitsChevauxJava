package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEchelle extends CaseColoree {
	private int id;

	public CaseEchelle(int id, Couleur couleur) {
		super(id,couleur);
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
