package fr.huautleroux.petitschevaux.entites;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.CaseDeChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
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
		
		return null; // Théoriquement impossible que ça se produise
	}
	
	public Case getCaseCible(Plateau plateau, int de, int indiceJoueur) {
		/*
		 * Ici tu gères seulement le cas des cases de Chemin, il faudra gérer le cas aussi lorsqu'on passe des cases de chemins aux cases d'échelles
		 */
		
		Case caseActuelle = getCaseActuelle(plateau);
		
		ArrayList<Case> cases = new ArrayList<Case>();
		plateau.getEchelles().get(couleur.ordinal()).forEach(c -> cases.add(c));
		plateau.getChemin().forEach(c -> cases.add(c));

		for (Case c : cases) {
			if(c.equals(caseActuelle)) {
				if (c instanceof CaseDeChemin) {
					int indice = plateau.getChemin().indexOf(c);
					indice += de;
					return plateau.getChemin().get(indice);
				}
				else if (c instanceof CaseEchelle){
					int indice = plateau.getEchelles().indexOf(c);
					indice += de;
					return plateau.getEchelles().get(indiceJoueur).get(indice);
				}
			}
		}
		
		
		
		return null;
	}
	
	public int getId() {
		return id;
	}

	public Couleur getCouleur() {
		return couleur;
	}
}
