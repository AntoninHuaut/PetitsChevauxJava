package fr.huautleroux.petitschevaux.affichage;

import fr.huautleroux.petitschevaux.core.Partie;

public class GererPartie {
	
	public void demarrerPartie(Partie partie) {
		if (partie == null) {
			partie = new Partie();
			partie.initialiserJeu();
		}

		partie.startJeu();
	}
}
