package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class JoueurBot extends Joueur {

	private static int nbBot = 0;
	private static String[] noms = new String[] { "Alpha", "Bêta", "Gamma", "Delta" };
	
	public JoueurBot(Couleur couleur) {
		super(noms[nbBot >= noms.length ? noms.length - 1 : nbBot++], couleur);
	}

	@Override
	public Pion choisirPion(int de, Plateau plateau) {
		System.out.println(getNom() + " a fait un " + de);

		return null;
	}

}