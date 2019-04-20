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
	
	public Case getCaseCible(Plateau plateau, int de) {
		/*
		 * Ici tu gères seulement le cas des cases de Chemin, il faudra gérer le cas aussi lorsqu'on passe des cases de chemins aux cases d'échelles
		 */
		
		Case caseActuelle = getCaseActuelle(plateau);
		int indiceJoueur = couleur.ordinal();
		
		ArrayList<Case> cases = new ArrayList<Case>();
		plateau.getEchelles().get(couleur.ordinal()).forEach(c -> cases.add(c));
		plateau.getChemin().forEach(c -> cases.add(c));

		for (Case c : cases) {
			if(c.equals(caseActuelle)) {
				if (c instanceof CaseDeChemin) {
					int indice = plateau.getChemin().indexOf(c);
					indice += de;
					indice %= plateau.getChemin().size();
					
					return plateau.getChemin().get(indice);
				}
				else if (c instanceof CaseEchelle){
					/*
					 * On peut avancer que de un en un sur les cases d'échelles
					 * 
					 * La montée à l’ ́echelle :
					 *  Après un tour complet du jeu, les pions se placent sur la dernière case de leur parcours,
					 *  ce qui les conduira à l’́echelle dont les cases sont nuḿerotées de 1 à 6.
					 *  Pour avancer sur la case numéro 1 il faut faire un 1 au dé. Puis, pour avancer sur la case numéro 2 il faut faire un 2 et ainsi de suite
					 *  jusqu’à la case numéro 6. Sur l’́echelle, les pions peuvent se trouver bloqués par des pions de leur propre couleur,
					 *  deux pions de la même couleur ne peuvent pas partager une case de l’́echelle.
					 */
					int indice = plateau.getEchelles().get(indiceJoueur).indexOf(c);
					indice %= plateau.getEchelles().get(indiceJoueur).size();
					
					return plateau.getEchelles().get(indiceJoueur).get(indice+1);
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
