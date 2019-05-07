package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.affichage.console.Utils;
import fr.huautleroux.petitschevaux.affichage.graphique.IGraphique;
import fr.huautleroux.petitschevaux.cases.CaseChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.PionFinParcoursException;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
		if (Main.utilise_Interface())
			updateAffichageInterface();
		else
			updateAffichageConsole();
	}

	private void updateAffichageConsole() {
		System.out.println("[PLATEAU INCROYABLE]");
		
		for (int y = 0; y < 15; y++)
			for (int x = 0; x < 15; x++) {
				Case caseCible = getCaseParCordonnee(x, y);

				if (caseCible == null)
					continue;

				if (caseCible instanceof CaseEcurie) {

				} else {

				}
			}
	}

	private void updateAffichageInterface() {
		for (int y = 0; y < 15; y++)
			for (int x = 0; x < 15; x++) {
				String id = x + "-" + y;

				Text text = IGraphique.getInstance().getAffichage().getTexts().get(id);
				text.setText("");

				Case caseCible = getCaseParCordonnee(x, y);

				if (caseCible == null)
					continue;

				if (caseCible instanceof CaseEcurie) {
					int numeroCheval = 0;
					numeroCheval += x <= 3 ? x%2 : x%11;
					int yTemp = (y <= 3 ? y%2 : y%11);
					numeroCheval += yTemp;

					if (yTemp != 0)
						numeroCheval++;

					Pion p = null;

					for (Pion pGet : caseCible.getChevaux())
						if (pGet.getId() == numeroCheval)
							p = pGet;

					if (p != null)
						text.setText(Couleur.SYMBOL + " " + (p.getId() + 1));
				} else {
					String numeroCases = "";
					Couleur couleur = null;

					for (Pion p : caseCible.getChevaux()) {
						couleur = p.getCouleur();
						numeroCases += (numeroCases.isEmpty() ? Couleur.SYMBOL + " " : ", ") + (p.getId() + 1);
					}

					if (!numeroCases.isEmpty()) {
						text.setText(numeroCases);
						text.setFill(couleur.getTextCouleurIG());
					}
				}
			}

		Text text = IGraphique.getInstance().getAffichage().getTexts().get("7-7");
		text.setText(Couleur.SYMBOL);
		text.setFill(Color.WHITE);
		text.setFont(new Font(40));
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
					IGraphique.getInstance().getAffichage().simpleMessage("🐎 Votre " + pion + " est sorti de l'écurie", pion.getCouleur().getTextCouleurIG());
				else
					partie.getAffichageInterface().simpleMessage(pion.getCouleur().getTextCouleurIC() + "🐎 Votre " + pion + " est sorti de l'écurie" + Utils.RESET, null);
			}
			else {
				if (Main.utilise_Interface())
					IGraphique.getInstance().getAffichage().simpleMessage("🏇 Votre " + pion + " s'est déplacé", pion.getCouleur().getTextCouleurIG());
				else
					partie.getAffichageInterface().simpleMessage(pion.getCouleur().getTextCouleurIC() +"🏇 Votre " + pion + " s'est déplacé" + Utils.RESET, null);
			}
		} else {
			if (Main.utilise_Interface())
				IGraphique.getInstance().getAffichage().simpleMessage("🐴 Votre " + pion + " n'a pas pu se déplacer", pion.getCouleur().getTextCouleurIG());
			else
				partie.getAffichageInterface().simpleMessage(pion.getCouleur().getTextCouleurIC() + "🐴 Votre " + pion + " n'a pas pu se déplacer" + Utils.RESET, null);
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
			
			if (Main.utilise_Interface())
				IGraphique.getInstance().getAffichage().simpleMessage("🐴 Le " + pion + " " + couleurPionRenvoye + " a été renvoyé à l'écurie", couleurPionRenvoye.getTextCouleurIG());
			else
				partie.getAffichageInterface().simpleMessage(couleurPionRenvoye.getTextCouleurIC() + "🐴 Le " + pion + " " + couleurPionRenvoye + " a été renvoyé à l'écurie" + Utils.RESET, null);
		}
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

				if (i == de && !caseTmp.peutSArreter(pion, de))
					deplacementPossible = false;

				if (i != de && !caseTmp.peutPasser(pion))
					deplacementPossible = false;

				i++;
			} while (caseTmp != caseCible && deplacementPossible);

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
	private Case getCaseParCordonnee(int x, int y) {
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
