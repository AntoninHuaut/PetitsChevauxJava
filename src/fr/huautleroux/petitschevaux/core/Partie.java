package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.JoueurBot;
import fr.huautleroux.petitschevaux.entites.JoueurHumain;
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

	public void initialiserJeu(Consumer<Partie> callback) {
		this.couleurCommence = tirageCouleur();

		int nbJoueur = Main.getPopStatic().getNombresJoueurs();

		initialiserJoueurs(nbJoueur, 4 - nbJoueur, () -> {
			initialiserPlateau();
			initialiserReference();
			Main.getAffStatic().tirageAuSort(couleurCommence, joueurs.get(couleurCommence.ordinal()).toString(), () -> callback.accept(this));
		});
	}

	public void initialiserJoueurs(int nbJoueur, int nbBot, Runnable callback) {
		HashMap<String, Couleur> pairs = Main.getPopStatic().getInitialisationJoueurs(nbJoueur);

		for (String nomJoueur : pairs.keySet())
			joueurs.add(new JoueurHumain(nomJoueur, pairs.get(nomJoueur)));

		for (int i = 0; i < nbBot; i++) {
			List<Couleur> couleurs = new ArrayList<Couleur>(Arrays.asList(Couleur.values()));
			joueurs.forEach(j -> couleurs.remove(j.getCouleur()));

			if (couleurs.isEmpty())
				return;

			joueurs.add(new JoueurBot(couleurs.get(0)));
		}

		joueurs = joueurs.stream().sorted((j1, j2) -> {
			int diff = j1.getCouleur().ordinal() - j2.getCouleur().ordinal();
			if (diff < 0) return -1;
			else if (diff > 0) return 1;
			else return 0;
		}).collect(Collectors.toList());

		callback.run();
	}

	public void initialiserPlateau() {
		this.plateau = new Plateau();

		for(int idJoueur = 0; idJoueur < joueurs.size(); idJoueur++)
			for(int idCheval = 0; idCheval < 4; idCheval++) {
				Pion pion = new Pion(idCheval, Couleur.values()[idJoueur]);
				plateau.getEcuries().get(idJoueur).ajouteCheval(pion);
			}
	}

	/*
	 * On initialise des listes avec des objets déjà crées
	 * Méthode indépendance pour qu'elle puisse être appelée par le système de chargement de sauvegarde
	 */
	public void initialiserReference() {
		this.plateau.setPartie(this);

		List<Case> cases = new ArrayList<Case>();
		cases.addAll(plateau.getEcuries());
		cases.addAll(plateau.getChemin());
		this.plateau.getEchelles().forEach(c -> cases.addAll(c));

		for(int idJoueur = 0; idJoueur < joueurs.size(); idJoueur++) {
			final int idJoueurFinal = idJoueur;
			Joueur j = joueurs.get(idJoueur);
			j.setCaseDeDepart(plateau.getChemin().get(1 + idJoueur * 14));
			j.initialisationReference();

			cases.forEach(c -> {
				if (c instanceof CaseColoree && ((CaseColoree) c).getCouleur().ordinal() != idJoueurFinal)
					return;

				c.getChevaux().forEach(pion -> {
					if (pion.getCouleur().equals(j.getCouleur()))
						j.ajouterCheval(pion);
				});
			});
		}

		cases.forEach(c -> c.getChevaux().forEach(pion -> pion.setCaseActuelle(c)));
	}

	public void jouerJeu() {
		plateau.updateAffichage();
		Main.getAffStatic().debutTour(numeroTour);
		int idDepart = couleurCommence.ordinal();

		for (int i = idDepart; i < joueurs.size() + idDepart && !stopPartie; i++) {
			int nb = i % joueurs.size();
			this.idJoueurCourant = nb;

			plateau.updateAffichage();
			jouerJoueur(false, lancerDe());
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

	public void jouerJoueur(boolean aDejaFaitSix, int de) {
		Joueur joueurCourant = getJoueurCourant();
		if (!aDejaFaitSix)
			Main.getAffStatic().simpleMessage("C'est à " + joueurCourant + " de jouer !", joueurCourant.getCouleur().getPrincipalColor());
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
				jouerJoueur(aDejaFaitSix, de);
			}
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
			jouerJoueur(true, lancerDe());
		}
	}

	public boolean estPartieTerminee() {
		return !getJoueurGagnant().isEmpty();
	}

	public List<Joueur> getJoueurGagnant() {
		List<Joueur> gagnants = new ArrayList<Joueur>();

		for (Joueur j : joueurs) {
			boolean pionBienPlace = true;

			int countPion = 0;
			List<CaseEchelle> echelles = plateau.getEchelles().get(j.getCouleur().ordinal());

			for (int i = 0; i < echelles.size() && pionBienPlace; i++) {
				CaseEchelle caseEchelle = echelles.get(i);
				countPion += caseEchelle.getChevaux().size();

				// Si le pion est sur un case de l'échelle qui n'est pas 3-4-5-6
				// L'indice 1 correspond à la case 2
				if(i <= 1)
					pionBienPlace = false;
			}

			// Si tous les pions du joueur sont à l'échelles et que les pions sont dans placés dans les cases 3-4-5-6
			if(countPion == 4 && pionBienPlace)
				gagnants.add(j);
		}

		return gagnants;
	}

	public Joueur getJoueurCourant() {
		return joueurs.get(idJoueurCourant);
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setStopPartie(boolean stopPartie) {
		this.stopPartie = stopPartie;
	}

	private int lancerDe() {
		int lanceN = random.nextInt(6) + 1;
		return lanceN;
	}


	private Couleur tirageCouleur(){
		int de = random.nextInt(4);
		Couleur[] couleurs = Couleur.values();
		return couleurs[de];
	}
}
