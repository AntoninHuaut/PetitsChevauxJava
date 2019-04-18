package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;


import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseDeChemin;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Plateau {

	private ArrayList<ArrayList<CaseEchelle>> echelles = new ArrayList<ArrayList<CaseEchelle>>();
	private ArrayList<CaseDeChemin> chemin = new ArrayList<CaseDeChemin>();
	private ArrayList<CaseEcurie> ecurie = new ArrayList<CaseEcurie>();

	public Plateau() {
		Couleur[] couleurs = Couleur.values();

		for (int i = 0; i < 4; i++) {
			this.ecurie.add(new CaseEcurie(couleurs[i]));

			for (int j = 0; j < 12; j++)
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
		boolean peutPasser = caseCible.peutPasser(pion);
		boolean peutSArreter = caseCible.peutSArreter(pion);
		if(peutPasser && peutSArreter) {
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
