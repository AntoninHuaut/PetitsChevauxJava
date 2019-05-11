package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.cases.CaseChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.PionFinParcoursException;

public class Plateau {

	private transient Partie partie;
	private List<List<CaseEchelle>> echelles = new ArrayList<List<CaseEchelle>>();
	private List<CaseChemin> chemin = new ArrayList<CaseChemin>();
	private List<CaseEcurie> ecurie = new ArrayList<CaseEcurie>();

	/**
	 * Initialisation des cases
	 */
	public Plateau() {
		Couleur[] couleurs = Couleur.values();

		for (int i = 0; i < 4; i++) {
			this.ecurie.add(new CaseEcurie(couleurs[i], i));

			for (int j = 0; j < 14; j++)
				this.chemin.add(new CaseChemin(i*14 + j));

			List<CaseEchelle> echelle = new ArrayList<CaseEchelle>();

			for (int j = 0; j < 6; j++)
				echelle.add(new CaseEchelle(couleurs[i], i*6 + j));

			echelles.add(echelle);
		}
	}

	/**
	 * Met à jour l'affichage graphique du plateau
	 */
	public void updateAffichage() {
		partie.getGererPartie().getInterface().miseAJourAffichage(this);
	}

	/**
	 * Tente le déplacement un pion
	 * @param pion Pion à ajouter
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 */
	public void deplacerPionA(Pion pion, int de) {
		if (isDeplacementPossible(pion, de)) {
			Case ancienneCase = pion.getCaseActuelle();
			Case nouvelleCase;

			try {
				nouvelleCase = getCaseCible(pion, de);
			} catch (PionFinParcoursException e) {
				System.err.println(e.getMessage());
				return;
			}

			ancienneCase.retirerCheval(pion);
			nouvelleCase.ajouteCheval(pion);
			mangerLesPions(pion.getCouleur(), nouvelleCase);

			if (ancienneCase instanceof CaseEcurie) {
				if (Main.utilise_Interface())
					partie.getGererPartie().getInterface().simpleMessage("Votre " + pion + " est sorti de l'écurie", pion.getCouleur().getTextCouleurIG());
				else
					partie.getGererPartie().getInterface().simpleMessage("Votre " + pion + " est sorti de l'écurie", pion.getCouleur().getTextCouleurIC());
			}
			else {
				if (Main.utilise_Interface())
					partie.getGererPartie().getInterface().simpleMessage("🏇 Votre " + pion + " s'est déplacé", pion.getCouleur().getTextCouleurIG());
				else
					partie.getGererPartie().getInterface().simpleMessage("Votre " + pion + " s'est déplacé", pion.getCouleur().getTextCouleurIC());
			}
		} else {
			if (Main.utilise_Interface())
				partie.getGererPartie().getInterface().simpleMessage("🐴 Votre " + pion + " n'a pas pu se déplacer",pion.getCouleur().getTextCouleurIG());
			else
				partie.getGererPartie().getInterface().simpleMessage("Votre " + pion + " n'a pas pu se déplacer",pion.getCouleur().getTextCouleurIC());
		}
	}

	/**
	 * Mange les pions des autres joueurs sur une case donnée
	 * @param couleur Couleur du pion qui se déplace
	 * @param caseCible Case où le pion se déplace
	 */
	private void mangerLesPions(Couleur couleur, Case caseCible) {
		List<Pion> pions = new ArrayList<Pion>(caseCible.getChevaux());

		for (Pion pion : pions) {
			if (pion.getCouleur().equals(couleur))
				continue;

			pion.getCaseActuelle().retirerCheval(pion);
			Couleur couleurPionRenvoye = pion.getCouleur();
			getEcuries().get(couleurPionRenvoye.ordinal()).ajouteCheval(pion);

			partie.getGererPartie().getInterface().simpleMessage("🐴 Le " + pion + " " + couleurPionRenvoye + " a été renvoyé à l'écurie", Main.utilise_Interface() ? couleurPionRenvoye.getTextCouleurIG() : couleurPionRenvoye.getTextCouleurIC());}
	}

	/**
	 * Teste si le déplacement est possible
	 * @param pion Pion à ajouter
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 * @return Si le déplacement est possible
	 */
	public boolean isDeplacementPossible(Pion pion, int de) {
		try {
			Case caseCible = getCaseCible(pion, de);
			boolean deplacementPossible = true;

			Case caseTmp;
			int i = 1;

			do {
				caseTmp = getCaseCible(pion, i);
				
				System.out.println(i + " + " + caseTmp + " " + de);

				if (caseTmp == caseCible && !caseTmp.peutSArreter(pion, de))
					deplacementPossible = false;

				if (caseTmp != caseCible && !caseTmp.peutPasser(pion))
					deplacementPossible = false;

				i++;
			} while (i <= de && deplacementPossible);

			return deplacementPossible;
		} catch (PionFinParcoursException e) {
			return false;
		}
	}

	/**
	 * Obtient la case ciblée
	 * @param pion Pion à ajouter
	 * @param nbDeplacement Nombre de déplacements que le cheval doit effectuer
	 * @return Case ciblée
	 * @throws PionFinParcoursException Erreur générée si le pion ne peut plus avancer car il est a la dernière case
	 */
	public Case getCaseCible(Pion pion, int nbDeplacement) throws PionFinParcoursException {
		Case caseActuelle = pion.getCaseActuelle();
		int indiceJoueur = pion.getCouleur().ordinal();

		if (caseActuelle instanceof CaseEcurie)
			return getPartie().getJoueurCourant().getCaseDeDepart();

		if (caseActuelle instanceof CaseChemin) {
			List<CaseChemin> chemins = getChemin();
			CaseChemin caseChemin = (CaseChemin) caseActuelle;
			int caseNumero = caseChemin.getNumero();

			// Le joueur va effectuer la transition
			if (caseChemin.isAccesEchelle(indiceJoueur))
				return getEchelles().get(indiceJoueur).get(0);

			// Le joueur atteint la case de transition
			if (isTransition(caseNumero, nbDeplacement, indiceJoueur))
				return chemins.get(indiceJoueur * 14); // Le joueur est limité à la case de transition

			caseNumero += nbDeplacement;
			return chemins.get(caseNumero % chemins.size());

		} else {
			List<CaseEchelle> echelles = getEchelles().get(indiceJoueur);
			CaseEchelle caseEchelle = (CaseEchelle) caseActuelle;

			if (caseEchelle.getNumeroLocal() == echelles.size() - 1) // Son Pion est sur la dernière case de l'échelle
				throw new PionFinParcoursException();

			return echelles.get(caseEchelle.getNumeroLocal() + 1);
		}
	}

	/**
	 * Teste si en se déplacant, la case ciblé est une case de transition pour le joueur
	 * @param caseNumero Numéro de la case
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 * @param indiceJoueur Numéro du joueur
	 * @return Si une case est une case de transition
	 */
	private boolean isTransition(int caseNumero, int de, int indiceJoueur) {
		for (int i = 1; i < de + 1; i++)
			if((caseNumero + i) % 14 == 0 && (caseNumero + i) == indiceJoueur * 14)
				return true;

		return false;
	}

	/**
	 * Transition entre la position d'une case sur le plateau et une instance de case
	 * @param x Indice x
	 * @param y Indice y
	 * @return Case a la position donnée
	 */
	public Case getCaseParCordonnee(int x, int y) {
		// Cases plateaux standards (sauf cases pré-échelles)

		if (y == 6) {
			if (x < 7)
				return getChemin().get(1 + x);
			else if (x > 7)
				return getChemin().get(21 + (x-8));
		} else if (y == 8) {
			if (x < 7)
				return getChemin().get(49 + (6-x));
			else if (x > 7)
				return getChemin().get(29 + (14-x));
		}

		if (x == 6) {
			if (y < 6)
				return getChemin().get(8 + (5-y));
			else if (y > 8)
				return getChemin().get(43 + (14-y));
		} else if (x == 8) {
			if (y < 6)
				return getChemin().get(15 + y);
			else if (y > 8)
				return getChemin().get(36 + (y-9));
		}

		// Cases pré-échelles

		if (y == 7) {
			if (x == 0)
				return getChemin().get(0);
			else if (x == 14)
				return getChemin().get(28);
		} else if (x == 7) {
			if (y == 0)
				return getChemin().get(14);
			else if (y == 14)
				return getChemin().get(42);
		}

		// Cases échelles

		if (y == 7) {
			if (x < 7)
				return getEchelles().get(0).get(x-1);
			else if (x > 7)
				return getEchelles().get(2).get(13-x);
		} else if (x == 7) {
			if (y < 7)
				return getEchelles().get(1).get(y-1);
			else if (y > 7)
				return getEchelles().get(3).get(13-y);
		}

		// Cases écuries

		if ((x == 2 || x == 3) && (y == 2 || y == 3))
			return getEcuries().get(0);
		else if ((x == 11 || x == 12) && (y == 2 || y == 3))
			return getEcuries().get(1);
		else if ((x == 11 || x == 12) && (y == 11 || y == 12))
			return getEcuries().get(2);
		else if ((x == 2 || x == 3) && (y == 11 || y == 12))
			return getEcuries().get(3);

		// Pas de cases
		return null;
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
