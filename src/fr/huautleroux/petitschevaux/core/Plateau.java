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

	public Plateau() {
		Couleur[] couleurs = Couleur.values();

		for (int i = 0; i < 4; i++) {
			this.ecurie.add(new CaseEcurie(couleurs[i], i));

			for (int j = 0; j < 13; j++)
				this.chemin.add(new CaseChemin(i*13 + j));

			List<CaseEchelle> echelle = new ArrayList<CaseEchelle>();

			for (int j = 0; j < 6; j++)
				echelle.add(new CaseEchelle(couleurs[i], i*6 + j));

			echelles.add(echelle);
		}
	}

	public void afficher() {
		List<Case> cases = new ArrayList<Case>();
		cases.addAll(getEcuries());
		cases.addAll(getChemin());
		getEchelles().forEach(c -> cases.addAll(c));

		System.out.println("Affichage de Cases");

		for (int i = 0; i < cases.size(); i++) // Affichage de la liste en tableau 1D =/= ce qu'on veut...
			System.out.println(cases.get(i));



	}

	public void deplacerPionA(Pion pion, Plateau plateau, int de) {
		if (pion.isDeplacementPossible(plateau, de)) {
			Case ancienneCase = pion.getCaseActuelle();
			Case nouvelleCase = pion.getCaseCible(plateau, de);
			ancienneCase.retirerCheval(pion);
			nouvelleCase.ajouteCheval(pion);

			if (ancienneCase instanceof CaseEcurie)
				System.out.println("Votre " + pion + " est sorti de l'écurie");
			else
				System.out.println("Votre " + pion + " s'est déplacé");
		} else
			System.out.println("Votre " + pion + " n'a pas pu se déplacer");
	}

	public List<CaseEcurie> getEcuries() {
		return ecurie;
	}

	public  List<List<CaseEchelle>> getEchelles() {
		return echelles;
	}

	public List<CaseChemin> getChemin(){
		return chemin;
	}

	public Partie getPartie() {
		return partie;
	}
	
	public void setPartie(Partie partie) {
		this.partie = partie;
	}
}
