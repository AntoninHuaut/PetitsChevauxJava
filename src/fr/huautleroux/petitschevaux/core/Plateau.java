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
			this.ecurie.add(new CaseEcurie(couleurs[i]));

			for (int j = 0; j < 12; j++)
				this.chemin.add(new CaseDeChemin());

			for (int f = 0; f < 7; f++) {
				/*
				 * Ici il faut créer une liste pour chaque couleur, qui contiendra la liste des cases du centre correspondant à la couleur (1 à 6 je crois)
				 * 
				 * JAUNE: 1 - 2 - 3 - 4 - 5 - 6
				 * BLEU:  1 - 2 - 3 - 4 - 5 - 6
				 * VERT:  1 - 2 - 3 - 4 - 5 - 6
				 * ROUGE: 1 - 2 - 3 - 4 - 5 - 6
				 * 
				 * Donc tu as 4 listes après, et ces 4 listes, tu les mets dans une liste, tu obtiens donc une liste de listes de case d'échelle
				 */
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
