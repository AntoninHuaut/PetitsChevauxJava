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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Plateau {

	private final boolean DEBUG = false;

	private transient Partie partie;
	private List<List<CaseEchelle>> echelles = new ArrayList<List<CaseEchelle>>();
	private List<CaseChemin> chemin = new ArrayList<CaseChemin>();
	private List<CaseEcurie> ecurie = new ArrayList<CaseEcurie>();

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

	public void updateAffichage() {
		for (int y = 0; y < 15; y++)
			for (int x = 0; x < 15; x++) {
				String id = x + "-" + y;
				Text text = Main.getAffStatic().getTexts().get(id);
				text.setText("");
				Case caseGet = getCaseParCordonnee(x, y);
				
				if (caseGet == null)
					continue;

				if (caseGet instanceof CaseEcurie) {
					int numeroCheval = 0;
					numeroCheval += x <= 3 ? x%2 : x%11;
					int yTemp = (y <= 3 ? y%2 : y%11);
					numeroCheval += yTemp;

					if (yTemp != 0)
						numeroCheval++;

					Pion p = null;

					for (Pion pGet : caseGet.getChevaux())
						if (pGet.getId() == numeroCheval)
							p = pGet;

					if (p != null)
						text.setText(p.getCouleur().getSymbol() + " " + (p.getId() + 1));
				} else {
					String numeroCases = "";
					Couleur couleur = null;
					
					for (Pion p : caseGet.getChevaux()) {
						couleur = p.getCouleur();
						numeroCases += (numeroCases.isEmpty() ? couleur.getSymbol() + " " : ", ") + (p.getId() + 1);
					}
					
					if (!numeroCases.isEmpty()) {
						text.setText(numeroCases);
						text.setFill(couleur.getPrincipalColor());
					}
				}

				if (DEBUG) {
					if (caseGet instanceof CaseEchelle)
						text.setText("" + ((CaseEchelle) caseGet).getNumeroLocal());
					else
						text.setText("" + caseGet.getNumero());
				}
			}
		
		updateSymboleEcurie(0, 0, Couleur.JAUNE);
		updateSymboleEcurie(9, 0, Couleur.BLEU);
		updateSymboleEcurie(9, 9, Couleur.VERT);
		updateSymboleEcurie(0, 9, Couleur.ROUGE);
	}

	public void updateSymboleEcurie(int xDebut, int yDebut, Couleur couleur) {
		for (int y = yDebut; y < yDebut + 6; y++)
			for (int x = xDebut; x < xDebut + 6; x++) {
				if (x != xDebut && y != yDebut && x != 5+xDebut && y != 5+yDebut)
					continue;

				String id = x + "-" + y;
				Text text = Main.getAffStatic().getTexts().get(id);
				text.setText(couleur.getSymbol() + "");
				text.setFill(Color.WHITE);
				text.setFont(new Font(30));
			}
	}

	public void deplacerPionA(Pion pion, int de) {
		if (pion.isDeplacementPossible(this, de)) {
			Case ancienneCase = pion.getCaseActuelle();
			Case nouvelleCase;

			try {
				nouvelleCase = pion.getCaseCible(this, de);
			} catch (PionFinParcoursException e) {
				System.err.println(e.getMessage());
				return;
			}

			ancienneCase.retirerCheval(pion);
			nouvelleCase.ajouteCheval(pion);

			if (ancienneCase instanceof CaseEcurie)
				Main.getAffStatic().simpleMessage("Votre " + pion + " est sorti de l'écurie", pion.getCouleur().getPrincipalColor());
			else
				Main.getAffStatic().simpleMessage("Votre " + pion + " s'est déplacé", pion.getCouleur().getPrincipalColor());

			if (!(nouvelleCase instanceof CaseEchelle))
				mangerLesPions(pion.getCouleur(), nouvelleCase);
		} else
			Main.getAffStatic().simpleMessage("Votre " + pion + " n'a pas pu se déplacer", pion.getCouleur().getPrincipalColor());
	}

	public void mangerLesPions(Couleur couleur, Case caseCible) {
		for (Pion pion : caseCible.getChevaux()) {
			if (pion.getCouleur().equals(couleur))
				continue;

			pion.getCaseActuelle().retirerCheval(pion);
			getEcuries().get(couleur.ordinal()).ajouteCheval(pion);
			Main.getAffStatic().simpleMessage("Le " + pion + " " + couleur + " a été renvoyé à l'écurie", couleur.getPrincipalColor());
		}
	}

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
