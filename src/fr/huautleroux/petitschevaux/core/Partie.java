package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.huautleroux.petitschevaux.PetitsChevaux;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.JoueurBot;
import fr.huautleroux.petitschevaux.entites.JoueurHumain;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.exceptions.AucunPionException;
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;
import fr.huautleroux.petitschevaux.utils.Saisie;
import fr.huautleroux.petitschevaux.utils.Utils;

public class Partie {

	private List<Joueur> joueurs = new ArrayList<Joueur>();

	private Couleur couleurCommence;
	private Plateau plateau = null;
	private Random random = new Random();

	private int idJoueurCourant = 0;
	private int numeroTour = 1;
	private boolean stopPartie = false;

	public void initialiserJeu() {
		this.couleurCommence = tirageCouleur();

		int nbJoueur;

		do {
			System.out.print("Entrez le nombre de joueurs qui vont participer : ");
			nbJoueur = Saisie.asInt();
			System.out.println("");
		} while (nbJoueur > 4 || nbJoueur < 0);

		initialiserJoueurs(nbJoueur, 4 - nbJoueur);
		initialiserPlateau();
		initialiserReference();
	}

	public void initialiserJoueurs(int nbJoueur, int nbBot) {
		for (int i = 0; i < nbJoueur; i++) {
			System.out.println("Nouveau Joueur");
			System.out.print("  Entrez votre pseudo : ");
			String nom = Saisie.asStringNoEmpty();
			System.out.print("  Entrez la couleur que vous souhaitez : ");
			Couleur choixCouleur;

			do {
				choixCouleur = Saisie.asCouleur();
				boolean containsCouleur = false;

				for (Joueur j : joueurs)
					if (j.getCouleur().equals(choixCouleur))
						containsCouleur = true;

				if (containsCouleur) {
					System.out.print("  Cette couleur est déjà prise : ");
					choixCouleur = null;
				}
			} while (choixCouleur == null);

			System.out.println("");
			joueurs.add(new JoueurHumain(nom, choixCouleur));
		}

		for (int i = 0; i < nbBot; i++) {
			List<Couleur> couleurs = new ArrayList<Couleur>(Arrays.asList(Couleur.values()));
			joueurs.forEach(j -> couleurs.remove(j.getCouleur()));

			if (couleurs.isEmpty())
				return;

			joueurs.add(new JoueurBot(couleurs.get(0)));
		}

		System.out.println(couleurCommence.getTextColor() + "C'est " + joueurs.get(couleurCommence.ordinal()) + " qui commence en premier !" + Utils.RESET);
		System.out.println("Appuyez sur [Entrer] pour commencer la partie");
		Saisie.asString();
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

	public void startJeu() {
		while(!estPartieTerminee() && !stopPartie) {
			jouerUnTour();
			idJoueurCourant = 0;

			//break; // Evitez boucle infini pour les tests
		}
	}

	public void jouerUnTour() {
		Utils.effacerAffichage();
		System.out.println(Utils.BLUE_BOLD + Utils.WHITE_BACKGROUND + "TOUR N°" + numeroTour + Utils.RESET);

		int idDepart = couleurCommence.ordinal();

		for (int i = idDepart; i < joueurs.size() + idDepart && !stopPartie; i++) {
			int nb = i % joueurs.size();
			this.idJoueurCourant = nb;

			System.out.println(getJoueurCourant().getCouleur().getTextColor() + "Au tour de " + getJoueurCourant() + Utils.RESET);
			jouerJoueur(false, lancerDe());
		}

		numeroTour++;
	}

	public void jouerJoueur(boolean aDejaFaitSix, int de) {
		Joueur joueurCourant = getJoueurCourant();
		JoueurAction action = joueurCourant.choixAction(de, plateau);

		if (action.equals(JoueurAction.SAUVEGARDER)) {
			try {
				if(menuSauvegarde())
					return;
			} catch (SauvegardeException e) {
				System.err.println("La sauvegarde n'a pas pu s'effectuer : " + e.getMessage());
				System.out.println("Vous pouvez changer votre action");
				jouerJoueur(aDejaFaitSix, de);
			}
		}

		else if (action.equals(JoueurAction.SORTIR_CHEVAL) || action.equals(JoueurAction.DEPLACER_CHEVAL)) {
			Pion pion;
			try {
				pion = joueurCourant.choisirPion(de, action, plateau);
				System.out.println("");
				plateau.deplacerPionA(pion, de);
			} catch (AucunPionException e) {
				System.out.println("Aucun pion disponible, " + joueurCourant.getNom() + " a gagné");
			}
		}

		System.out.println("");

		if (de == 6 && !aDejaFaitSix) {
			System.out.println("Vous avez fait 6 ! Vous pouvez rejouer une deuxième fois");
			jouerJoueur(true, lancerDe());
		}
	}

	private boolean menuSauvegarde() throws SauvegardeException {
		System.out.println("Entrez le nom souhaité pour la sauvegarde");
		String nomSauvegarde = Saisie.asStringNoEmpty();
		nomSauvegarde = PetitsChevaux.getInstance().getSaveManager().convertSaveName(nomSauvegarde);
		boolean overwrite = false;

		if (PetitsChevaux.getInstance().getSaveManager().estSauvegardeValide(nomSauvegarde)) {
			System.out.println("Une sauvegarde existe avec ce nom, souhaitez-vous l'écraser ? (Oui/Non)");
			overwrite = Saisie.asBoolean();
		}

		PetitsChevaux.getInstance().getSaveManager().sauvegarderPartie(this, nomSauvegarde, overwrite);
		System.out.println("La partie a été sauvegarde sur le slot " + nomSauvegarde);

		System.out.println("Souhaitez-vous quitter la partie en cours ? (Oui/Non)");
		stopPartie = Saisie.asBoolean();

		return stopPartie;
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
