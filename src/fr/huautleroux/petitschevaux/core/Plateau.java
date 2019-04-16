package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseDeChemin;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Plateau {

	private ArrayList<ArrayList<CaseEchelle>> echelles;
	private ArrayList<CaseDeChemin> chemin;
	private ArrayList<CaseEcurie> ecurie;

	public Plateau() {
		Couleur[] couleurs = Couleur.values();

		for (int i = 0; i < 4; i++) {
			this.ecurie.add(new CaseEcurie(0,couleurs[i]));

			for (int j = 0; j < 12; j++)
				this.chemin.add(new CaseDeChemin());

			for (int f = 1; f < 7; f++) {
				ArrayList<CaseEchelle> echelle = new ArrayList<CaseEchelle>();
				echelle.add(new CaseEchelle(f,couleurs[i]));
				echelles.add(echelle);
			}
		}
	}

	public void afficher() {

	}

	public void deplacerPionA(Pion pion, Case caseCible) {

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
