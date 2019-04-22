package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;

import java.util.List;

import fr.huautleroux.petitschevaux.cases.CaseChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Plateau {

	private transient Partie partie;
	private List<List<CaseEchelle>> echelles = new ArrayList<List<CaseEchelle>>();
	private List<CaseChemin> chemin = new ArrayList<CaseChemin>();
	private List<CaseEcurie> ecurie = new ArrayList<CaseEcurie>();

	public Plateau(Partie partie) {
		this.partie = partie;
		
		Couleur[] couleurs = Couleur.values();

		for (int i = 0; i < 4; i++) {
			this.ecurie.add(new CaseEcurie(couleurs[i]));

			for (int j = 0; j < 13; j++)
				this.chemin.add(new CaseChemin());

			List<CaseEchelle> echelle = new ArrayList<CaseEchelle>();
			
			for (int k = 0; k < 7; k++)
				echelle.add(new CaseEchelle(couleurs[i]));
			
			echelles.add(echelle);
		}
	}

	public void afficher(Plateau plateau) {
		List<Case> cases = new ArrayList<Case>();
		plateau.getEcuries().forEach(c->cases.add(c));
		plateau.getChemin().forEach(c -> cases.add(c));
		plateau.getEchelles().forEach(c -> cases.add(c));
		
		Object[] tableau = cases.toArray();
		System.out.println("Affichage de Cases");
		for (int i=0; i<cases.size();i++) // Affichage de la liste en tableau 1D =/= ce qu'on veut...
			System.out.println(tableau[i]);
		
		
		
	}
	
	

	public void deplacerPionA(Pion pion, Case caseCible) {
		/*
		 * Il faut vérifier toutes les cases entre la case actuelle du Pion, et la caseCible, si il peut passer dessus
		 * Si il peut passer sur toutes les cases, alors on vérifie s'il peut s'arrêter sur la case cible
		 * Si il peut s'arrêter alors on effectue le déplacement
		 */
		
		
		boolean peutSArreter = caseCible.peutSArreter(pion);
	
		if(peutSArreter) {
			partie.mangerLesPions(caseCible);
			caseCible.ajouteCheval(this, pion);
		}
		else {
			System.out.println("Le cheval n'a pas pu se déplacer");
		}
	}
	
	public List<CaseEcurie> getEcuries(){
		return ecurie;
	}

	public  List<List<CaseEchelle>> getEchelles(){
		return echelles;
	}

	public List<CaseChemin> getChemin(){
		return chemin;
	}
}
