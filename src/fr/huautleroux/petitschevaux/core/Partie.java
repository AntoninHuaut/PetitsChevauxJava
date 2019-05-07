package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.affichage.AffichageInterface;
import fr.huautleroux.petitschevaux.affichage.console.IConsole;
import fr.huautleroux.petitschevaux.affichage.console.Utils;
import fr.huautleroux.petitschevaux.affichage.graphique.IGraphique;
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

	private transient AffichageInterface affichageInterface;

	private List<Joueur> joueurs = new ArrayList<Joueur>();

	private Couleur couleurCommence;
	private Plateau plateau = null;
	private Random random = new Random();

	private int idJoueurCourant = 0;
	private int numeroTour = 1;
	private boolean stopPartie = false;

	/**
	 * Lance le jeu
	 */
	public void jouerJeu() {
		if (Main.utilise_Interface())
			affichageInterface = IGraphique.getInstance().getAffichage();
		else
			affichageInterface = new IConsole();

		affichageInterface.tirageAuSort(couleurCommence, "" + getJoueurs().get(couleurCommence.ordinal()), () -> {
			plateau.updateAffichage();
			affichageInterface.debutTour(numeroTour);

			int idDepart = couleurCommence.ordinal();

			for (int i = idDepart; i < joueurs.size() + idDepart && !stopPartie; i++) {
				int nb = i % joueurs.size();
				this.idJoueurCourant = nb;

				tourJoueur(false, lancerDe());
				plateau.updateAffichage();

				if (estPartieTerminee()) {
					affichageInterface.finDePartie(numeroTour, getJoueurGagnant());
					return;
				}
			}

			if (stopPartie)
				return;

			numeroTour++;

			if (Main.utilise_Interface())
				IGraphique.getInstance().getAffichage().simpleMessage("Appuyez sur [Entrer] pour passer au tour suivant", Color.MEDIUMPURPLE);
			else
				affichageInterface.simpleMessage(Utils.PURPLE_BRIGHT + "Appuyez sur [Entrer] pour passer au tour suivant" + Utils.RESET, null);

			affichageInterface.attendreToucheEntrer(() -> {
				if(!estPartieTerminee() && !stopPartie) {
					idJoueurCourant = 0;
					jouerJeu();
				}
			});
		});
	}

	/**
	 * Effectue le tour de jeu du joueur courant
	 * @param aDejaFaitSix Si le joueur a été rejoué
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 */
	public void tourJoueur(boolean aDejaFaitSix, int de) {
		Joueur joueurCourant = getJoueurCourant();

		if (Main.utiliser_DeTruque()) {
			if (Main.utilise_Interface())
				de = IGraphique.getInstance().getPopup().getNombres(999, "De truqué", "Dé original : " + de, "Entrez la valeur du dé truquée : ");
			else
				de = ((IConsole) affichageInterface).getDeTruque(de);
		}

		if (!aDejaFaitSix) {
			if (Main.utilise_Interface())
				IGraphique.getInstance().getAffichage().simpleMessage("C'est à " + joueurCourant + " de jouer !", joueurCourant.getCouleur().getTextCouleurIG());
			else
				((IConsole) affichageInterface).simpleMessage(Utils.PURPLE_BRIGHT + "C'est à " + joueurCourant + " de jouer !" + Utils.RESET, null);
		}
		else
			affichageInterface.simpleMessage(joueurCourant + " peut rejouer une deuxième fois !\n", null);

		affichageInterface.simpleMessage(joueurCourant.getNom() + " a fait " + de, null);
		JoueurAction action = joueurCourant.choixAction(de, plateau);
		affichageInterface.simpleMessage(joueurCourant.getNom() + " a choisi de : " + action.getNom(), null);

		if (action.equals(JoueurAction.SAUVEGARDER)) {
			try {
				SauvegardeResultat sauvegardeResultat;

				if (Main.utilise_Interface())
					sauvegardeResultat = IGraphique.getInstance().getPopup().menuSauvegarde(this);
				else
					sauvegardeResultat = ((IConsole) affichageInterface).menuSauvegarde(this);

				if (sauvegardeResultat.equals(SauvegardeResultat.ANNULER))
					throw new SauvegardeException("Sauvegarde annulée");

				stopPartie = sauvegardeResultat.equals(SauvegardeResultat.QUITTER);

				if (Main.utilise_Interface())
					IGraphique.getInstance().getAffichage().simpleMessage("\nLa partie a été sauvegardée", Color.MEDIUMPURPLE);
				else
					((IConsole) affichageInterface).simpleMessage(Utils.PURPLE_BRIGHT + "\nLa partie a été sauvegardée" + Utils.RESET, null);

				if(stopPartie) {
					if (Main.utilise_Interface())
						IGraphique.getInstance().getAffichage().simpleMessage("\n• La partie s'est arrêtée •", Color.MEDIUMPURPLE);
					else
						((IConsole) affichageInterface).simpleMessage(Utils.PURPLE_BRIGHT + "\n• La partie s'est arrêtée •" + Utils.RESET, null);

					return;
				}
			} catch (SauvegardeException ex) {
				affichageInterface.simpleMessage("\nUne erreur est survenue pendant la sauvegarde :\n" + ex.getMessage() + "\n", null);
			}

			tourJoueur(aDejaFaitSix, de);
			return;
		}

		else if (action.equals(JoueurAction.SORTIR_CHEVAL) || action.equals(JoueurAction.DEPLACER_CHEVAL)) {
			Pion pion;
			try {
				pion = joueurCourant.choisirPion(de, action, plateau);
				affichageInterface.simpleMessage(joueurCourant.getNom() + " a choisi son pion n°" + (pion.getId() + 1), null);
				plateau.deplacerPionA(pion, de);
			} catch (AucunPionException e) {
				if (Main.utilise_Interface())
					IGraphique.getInstance().getPopup().showPopup(AlertType.ERROR, "Erreur de la détection de victoire", null, "Aucun pion disponible, " + joueurCourant.getNom() + " a gagné"); // Normalement n'arrive jamais ici
				else
					System.out.println("Erreur de la détection de victoire\nAucun pion disponible, " + joueurCourant.getNom() + " a gagné");
			}
		}

		else 
			affichageInterface.simpleMessage(joueurCourant.getNom() + " passe son tour", null);

		affichageInterface.simpleMessage("", null);

		if (de == 6 && !aDejaFaitSix) {
			plateau.updateAffichage();
			tourJoueur(true, lancerDe());
		}
	}

	/**
	 * Savoir si une partie est terminée
	 * @return Vrai si la partie est terminée
	 */
	public boolean estPartieTerminee() {
		return getJoueurGagnant() != null;
	}

	/**
	 * Obtient le gagnant de la partie
	 * @return Joueur gagnant
	 */
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

	/**
	 * Simule un lancé de dé
	 * @return Nombre entier entre 1 et 6
	 */
	private int lancerDe() {
		int lanceN = random.nextInt(6) + 1;
		return lanceN;
	}

	/**
	 * Trie l'ordre des joueurs pour qu'il soit dans l'ordre des couleurs
	 */
	public void trierOrdreJoueurs() {
		joueurs = joueurs.stream().sorted((j1, j2) -> j1.compareTo(j2)).collect(Collectors.toList());
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

	public AffichageInterface getAffichageInterface() {
		return affichageInterface;
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
