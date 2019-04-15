package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Collection;

import fr.huautleroux.petitschevaux.cases.Case;
import fr.huautleroux.petitschevaux.cases.CaseDEchelle;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.players.Pion;
import fr.huautleroux.petitschevaux.cases.CaseDeChemin;


public class Plateau {
	private ArrayList<ArrayList<CaseDEchelle>> echelles;
	private ArrayList<CaseDeChemin> chemin;
	private ArrayList<CaseEcurie> ecurie;

	
	Plateau(){
		
		for (int i =0; i<4;i++) {
			this.ecurie.add(new CaseEcurie(/*ID, Couleur ?*/));
			for (int j = 0; j<12;j++) {
				this.chemin.add(new CaseDeChemin(/*Id, Couleur ?*/));
			}
			for (int f = 0; f<7; f++) {
			this.echelles.addAll(echelle(new CaseDEchelle(/*Id, Couleur ?*/)); /*Liste de liste ?*/
			}
		}
	}
	
	

	
	public ArrayList<CaseEcurie> getEcuries(){
		return this.ecurie;
	}
	
	public  ArrayList<ArrayList<CaseDEchelle>> getEchelles(){
		return this.echelles;
	}
	
	public ArrayList<CaseDeChemin> getChemin(){
		return this.chemin;
	}
	
	public void afficher() {
		
	}
	
	public void deplacerPionA(Pion p, Case c) {
		
	}
}
