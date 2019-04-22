package fr.huautleroux.petitschevaux.entites;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.cases.CaseChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.PionFinParcoursException;

public class Pion implements Comparable<Pion> {

	private transient Case caseActuelle = null;
	private int id;
	private Couleur couleur;

	public Pion(int id, Couleur couleur) {
		this.id = id;
		this.couleur = couleur;
	}

	public boolean isDeplacementPossible(Plateau plateau, int de) {
		try {
			Case caseCible = getCaseCible(plateau, de);
			boolean deplacementPossible = true;

			Case caseTmp;
			int i = 1;


			do {
				caseTmp = getCaseCible(plateau, i);

				if (!caseTmp.peutPasser(this) || !caseTmp.peutSArreter(this, de))
					deplacementPossible = false;

				i++;
			} while (caseTmp != caseCible && deplacementPossible);

			return deplacementPossible;
		} catch (PionFinParcoursException e) {
			return false;
		}
	}

	public Case getCaseCible(Plateau plateau, int nbDeplacement) throws PionFinParcoursException {
		Case caseActuelle = getCaseActuelle();
		int indiceJoueur = couleur.ordinal();

		if (caseActuelle instanceof CaseEcurie)
			return plateau.getPartie().getJoueurCourant().getCaseDeDepart();

		List<Case> cases = new ArrayList<Case>();

		if (caseActuelle instanceof CaseEchelle)
			plateau.getEchelles().get(couleur.ordinal()).forEach(c -> cases.add(c));
		else
			plateau.getChemin().forEach(c -> cases.add(c));

		if (caseActuelle instanceof CaseChemin) {
			List<CaseChemin> chemins = plateau.getChemin();
			CaseChemin caseChemin = (CaseChemin) caseActuelle;
			int caseNumero = caseChemin.getNumero();

			// Le joueur va effectuer la transition
			if (caseChemin.isAccesEchelle())
				return plateau.getEchelles().get(indiceJoueur).get(0);

			// Le joueur atteint la case de transition
			if (isTransition(caseNumero, nbDeplacement))
				return chemins.get(indiceJoueur * 14); // Le joueur est limité à la case de transition

			caseNumero += nbDeplacement;
			return chemins.get(caseNumero % chemins.size());

		} else {
			List<CaseEchelle> echelles = plateau.getEchelles().get(indiceJoueur);
			int indice = echelles.indexOf(caseActuelle);

			if (indice == echelles.size() - 1) // Son Pion est sur la dernière case de l'échelle
				throw new PionFinParcoursException();

			return echelles.get(indice + 1);
		}
	}

	private boolean isTransition(int caseNumero, int de) {
		for (int i = 1; i < de + 1; i++)
			if(caseNumero + de == 0 % 14)
				return true;

		return false;
	}

	@Override
	public String toString() {
		return "Pion n° " + (id + 1);
	}

	@Override
	public int compareTo(Pion pion) {
		return Integer.valueOf(this.getId()).compareTo(Integer.valueOf(pion.getId()));
	}

	public Case getCaseActuelle() {
		return caseActuelle;
	}

	public void setCaseActuelle(Case caseActuelle) {
		this.caseActuelle = caseActuelle;
	}

	public boolean isDansEcurie() {
		return caseActuelle instanceof CaseEcurie;
	}

	public int getId() {
		return id;
	}

	public Couleur getCouleur() {
		return couleur;
	}
}
