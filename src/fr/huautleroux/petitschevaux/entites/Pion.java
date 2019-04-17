package fr.huautleroux.petitschevaux.entites;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Pion {

	private int id;
	private Couleur couleur;

	public Pion(int id, Couleur couleur) {
		this.id = id;
		this.couleur = couleur;
	}

	public Case getCaseActuelle(Plateau plateau) {
		Case ecurie = plateau.getEcuries().get(couleur.ordinal());

		if(ecurie.getChevaux().contains(this))
			return ecurie;

		ArrayList<Case> cases = new ArrayList<Case>();
		plateau.getEchelles().get(couleur.ordinal()).forEach(c -> cases.add(c));
		plateau.getChemin().forEach(c -> cases.add(c));

		for(Case c : cases)
			if(c.getChevaux().contains(this))
				return c;
		
		return null;
	}
	
	public int getId() {
		return id;
	}

	public Couleur getCouleur() {
		return couleur;
	}
}
