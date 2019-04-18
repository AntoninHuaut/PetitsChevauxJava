package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;


import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseDeChemin;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Plateau {

	private Partie partie;
	private ArrayList<ArrayList<CaseEchelle>> echelles = new ArrayList<ArrayList<CaseEchelle>>();
	private ArrayList<CaseDeChemin> chemin = new ArrayList<CaseDeChemin>();
	private ArrayList<CaseEcurie> ecurie = new ArrayList<CaseEcurie>();

	public Plateau(Partie partie) {
		this.partie = partie;
		
		Couleur[] couleurs = Couleur.values();

		for (int i = 0; i < 4; i++) {
			this.ecurie.add(new CaseEcurie(couleurs[i]));

			for (int j = 0; j < 13; j++)
				this.chemin.add(new CaseDeChemin());

			ArrayList<CaseEchelle> echelle = new ArrayList<CaseEchelle>();
			
			for (int k = 0; k < 7; k++)
				echelle.add(new CaseEchelle(couleurs[i]));
			
			echelles.add(echelle);
		}
	}

	public void afficher() {

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
			caseCible.ajouteCheval(pion);
		}
		else {
			System.out.println("Le cheval n'a pas pu se déplacer");
		}
	}
	
	public ArrayList<CaseEcurie> getEcuries(){
		return ecurie;
	}

	public  ArrayList<ArrayList<CaseEchelle>> getEchelles(){
		return echelles;
	}

	public ArrayList<CaseDeChemin> getChemin(){
		return chemin;
	}
}
