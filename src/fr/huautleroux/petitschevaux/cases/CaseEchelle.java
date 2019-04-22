package fr.huautleroux.petitschevaux.cases;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.core.Plateau;
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
	
	public String toString(Plateau p) {
		List<Case> casesEchelle = new ArrayList<Case>();
		p.getEcuries().forEach(c -> casesEchelle.add(c));
		// To String différent selon des indices de la case
				
		String r = Integer.toString(casesEchelle.indexOf(this));
		return  r;
	}

}
