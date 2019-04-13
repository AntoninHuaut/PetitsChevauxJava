package fr.huautleroux.petitschevaux.cases;

public class CaseEcurie extends CaseColoree {

	CaseEcurie(Couleur){
		
	}
	
	@Override
	public boolean peutPasser() {
		return false;
	}

	@Override
	public boolean peutSArreter() {
		return false;
	}

}
