package fr.huautleroux.petitschevaux.cases;

public class CaseDEchelle extends CaseColoree{

	CaseDEchelle(Couleur){
		
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
