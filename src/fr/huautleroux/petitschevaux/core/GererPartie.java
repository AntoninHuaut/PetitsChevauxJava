package fr.huautleroux.petitschevaux.core;

public class GererPartie {

	public void demarrerPartie(Partie partie) {
		if (partie == null) {
			partie = new Partie();
			partie.initialiserJeu((p) -> p.jouerJeu());
		}
		else
			partie.jouerJeu();
	}
}
