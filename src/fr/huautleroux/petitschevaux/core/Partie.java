package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.enums.SauvegardeResultat;
import fr.huautleroux.petitschevaux.exceptions.AucunPionException;
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class Partie {

	private List<Joueur> joueurs = new ArrayList<Joueur>();

	private Couleur couleurCommence;
	private Plateau plateau = null;
	private Random random = new Random();

	private int idJoueurCourant = 0;
	private int numeroTour = 1;
	private boolean stopPartie = false;

	public void jouerJeu() {
		plateau.updateAffichage();
		
		Main.getAffStatic().debutTour(numeroTour);
		int idDepart = couleurCommence.ordinal();

		for (int i = idDepart; i < joueurs.size() + idDepart && !stopPartie; i++) {
			int nb = i % joueurs.size();
			this.idJoueurCourant = nb;

			tourJoueur(false, lancerDe());
			plateau.updateAffichage();

			if (estPartieTerminee()) {
				Main.getAffStatic().finDePartie(numeroTour, getJoueurGagnant());
				return;
			}
		}

		if (stopPartie)
			return;

		numeroTour++;

		Main.getAffStatic().simpleMessage("Appuyez sur [Entrer] pour passer au tour suivant", Color.MEDIUMPURPLE);
		Main.getAffStatic().attendreToucheEntrer(() -> {
			if(!estPartieTerminee() && !stopPartie) {
				idJoueurCourant = 0;
				jouerJeu();
			}
		});
	}

	public void tourJoueur(boolean aDejaFaitSix, int de) {
		Joueur joueurCourant = getJoueurCourant();
		if (!aDejaFaitSix)
			Main.getAffStatic().simpleMessage("C'est à " + joueurCourant + " de jouer !", joueurCourant.getCouleur().getTextCouleur());
		Main.getAffStatic().simpleMessage(joueurCourant.getNom() + " a fait " + de, null);
		JoueurAction action = joueurCourant.choixAction(de, plateau);
		Main.getAffStatic().simpleMessage(joueurCourant.getNom() + " a choisi de : " + action.getNom(), null);

		if (action.equals(JoueurAction.SAUVEGARDER)) {
			try {
				SauvegardeResultat sauvegardeResultat = Main.getPopStatic().menuSauvegarde(this);

				if (sauvegardeResultat.equals(SauvegardeResultat.ANNULER))
					throw new SauvegardeException("Sauvegarde annulée");

				stopPartie = sauvegardeResultat.equals(SauvegardeResultat.QUITTER);
				Main.getAffStatic().simpleMessage("\nLa partie a été sauvegardée", Color.MEDIUMPURPLE);

				if(stopPartie) {
					Main.getAffStatic().simpleMessage("\n• La partie s'est arrêtée •", Color.MEDIUMPURPLE);
					return;
				}
			} catch (SauvegardeException ex) {
				Main.getAffStatic().simpleMessage("\nUne erreur est survenue pendant la sauvegarde :\n" + ex.getMessage() + "\n", Color.MEDIUMPURPLE);
			}

			tourJoueur(aDejaFaitSix, de);
			return;
		}

		else if (action.equals(JoueurAction.SORTIR_CHEVAL) || action.equals(JoueurAction.DEPLACER_CHEVAL)) {
			Pion pion;
			try {
				pion = joueurCourant.choisirPion(de, action, plateau);
				Main.getAffStatic().simpleMessage(joueurCourant.getNom() + " a choisi son pion n°" + (pion.getId() + 1), null);
				plateau.deplacerPionA(pion, de);
			} catch (AucunPionException e) {
				Main.getPopStatic().showPopup(AlertType.ERROR, "Erreur de la détection de victoire", null,"Aucun pion disponible, " + joueurCourant.getNom() + " a gagné"); // Normalement n'arrive jamais ici
			}
		}

		else 
			Main.getAffStatic().simpleMessage(joueurCourant.getNom() + " passe son tour", null);

		Main.getAffStatic().simpleMessage("", null);

		if (de == 6 && !aDejaFaitSix) {
			Main.getAffStatic().simpleMessage(joueurCourant.getNom() + " peut rejouer une deuxième fois !\n", null);
			plateau.updateAffichage();
			tourJoueur(true, lancerDe());
		}
	}

	public boolean estPartieTerminee() {
		return getJoueurGagnant() != null;
	}

	public Joueur getJoueurGagnant() {
		for (Joueur j : joueurs) {
			boolean pionBienPlace = true;

			int comptagePion = 0;
			List<CaseEchelle> echelles = plateau.getEchelles().get(j.getCouleur().ordinal());

			for (int i = 0; i < echelles.size() && pionBienPlace; i++) {
				CaseEchelle caseEchelle = echelles.get(i);
				boolean aPionEchelle = !caseEchelle.getChevaux().isEmpty();
				comptagePion += aPionEchelle ? 1 : 0;

				// Si le pion est sur un case de l'échelle qui n'est pas 3-4-5-6
				// L'indice 1 correspond à la case 2
				if(i <= 1 && aPionEchelle)
					pionBienPlace = false;
			}

			// Si tous les pions du joueur sont à l'échelles et que les pions sont dans placés dans les cases 3-4-5-6
			if(comptagePion == 4 && pionBienPlace)
				return j;
		}

		return null;
	}
	
	private int lancerDe() {
		int lanceN = random.nextInt(6) + 1;
		return lanceN;
	}
	
	public void trierOrdreJoueurs() {
		joueurs = joueurs.stream().sorted((j1, j2) -> {
			int diff = j1.getCouleur().ordinal() - j2.getCouleur().ordinal();
			if (diff < 0) return -1;
			else if (diff > 0) return 1;
			else return 0;
		}).collect(Collectors.toList());
	}

	public Joueur getJoueurCourant() {
		return joueurs.get(idJoueurCourant);
	}

	public Plateau getPlateau() {
		return plateau;
	}
	
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setStopPartie(boolean stopPartie) {
		this.stopPartie = stopPartie;
	}
	
	public Couleur getCouleurCommence() {
		return couleurCommence;
	}
	
	public void setCouleurCommence(Couleur couleurCommence) {
		this.couleurCommence = couleurCommence;
	}
	
	public Random getRandom() {
		return random;
	}
}
