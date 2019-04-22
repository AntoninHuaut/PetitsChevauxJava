package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
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
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class Partie {

	private List<Joueur> joueurs = new ArrayList<Joueur>();

	private Joueur joueurCourant = null;
	private Plateau plateau = null;
	private Random random = new Random();

	private int nbJoueurHumain;
	private boolean stopPartie = false;

	public void initialiserJeu() {
		int nb;

		do {
			System.out.print("Entrez le nombre de joueurs qui vont participer : ");
			nb = Saisie.asInt();
			System.out.println("");
		} while (nb > 4 || nb < 1);

		initialiserJoueurs(nb);
		initialiserPlateau();
		initialiserReference();
	}

	public void initialiserJoueurs(int nb) {
		this.nbJoueurHumain = nb;
		Couleur[] couleurs = Couleur.values();

		for(int i = 0; i < nbJoueurHumain; i++) {
			System.out.println("Joueur " + couleurs[i]);
			System.out.print("  Entrez votre pseudo : ");
			String nom = Saisie.asString();
			System.out.println("");
			joueurs.add(new JoueurHumain(nom, couleurs[i]));
		}

		for(int i = 0; i < (4 - nbJoueurHumain); i++)
			joueurs.add(new JoueurBot(couleurs[nbJoueurHumain + i]));
	}

	public void initialiserPlateau() {
		this.plateau = new Plateau(this);

		for(int idJoueur = 0; idJoueur < joueurs.size(); idJoueur++)
			for(int idCheval = 0; idCheval < 4; idCheval++) {
				Pion pion = new Pion(idCheval, Couleur.values()[idJoueur]);
				plateau.getEcuries().get(idJoueur).ajouteCheval(plateau, pion);
			}
	}

	/*
	 * On initialise des listes avec des objets déjà crées
	 * Méthode indépendance pour qu'elle puisse être appelée par le système de chargement de sauvegarde
	 */
	public void initialiserReference() {
		List<Case> cases = new ArrayList<Case>();
		cases.addAll(plateau.getEcuries());
		cases.addAll(plateau.getChemin());
		plateau.getEchelles().forEach(c -> cases.addAll(c));

		for(int idJoueur = 0; idJoueur < joueurs.size(); idJoueur++) {
			final int idJoueurFinal = idJoueur;
			Joueur j = joueurs.get(idJoueur);
			j.setCaseDeDepart(plateau.getChemin().get(idJoueur * 14));
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

			break; // Evitez boucle infini pour les tests
		}
	}

	public void jouerUnTour() {
		for (int i = 0; i < joueurs.size() && !stopPartie; i++) {
			setJoueurCourant(joueurs.get(i));

			System.out.println("Au tour de " + joueurCourant.getNom() + " (" + joueurCourant.getCouleur() + ")");

			int de = lancerDe();
			JoueurAction action = joueurCourant.choixAction(de, plateau);

			if (action.equals(JoueurAction.SAUVEGARDER)) {
				if(menuSauvegarde())
					return;
			}

			else if (action.equals(JoueurAction.SORTIR_CHEVAL) || action.equals(JoueurAction.DEPLACER_CHEVAL)) {
				Pion pion = joueurCourant.choisirPion(de, action);
				// Pion renvoie null car la fonction n'est pas terminée, c'est en commentaire pour faire des tests sans que ça crash
				//Case caseCible = pion.getCaseCible(plateau, de);
				//plateau.deplacerPionA(pion, caseCible);
			}

			System.out.println("");
		}
	}

	private boolean menuSauvegarde() {
		System.out.println("Entrez le nom souhaité pour la sauvegarde");
		String nomSauvegarde = Saisie.asString();
		nomSauvegarde = PetitsChevaux.getInstance().getSaveManager().convertSaveName(nomSauvegarde);
		boolean overwrite = false;

		if (PetitsChevaux.getInstance().getSaveManager().estSauvegardeValide(nomSauvegarde)) {
			System.out.println("Une sauvegarde existe avec ce nom, souhaitez-vous l'écraser ? (Oui/Non)");
			overwrite = Saisie.asBoolean();
		}

		try {
			PetitsChevaux.getInstance().getSaveManager().sauvegarderPartie(this, nomSauvegarde, overwrite);
			System.out.println("La partie a été sauvegarde sur le slot " + nomSauvegarde);

			System.out.println("Souhaitez-vous quitter la partie en cours ? (Oui/Non)");
			stopPartie = Saisie.asBoolean();
		} catch (SauvegardeException e) {
			System.err.println("La sauvegarde n'a pas pu s'effectuer : " + e.getMessage());
		}

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
		return joueurCourant;
	}

	public void setJoueurCourant(Joueur joueur) {
		this.joueurCourant = joueur;
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

	public void mangerLesPions(Case caseCible) {

	}

	private int lancerDe() {
		int lanceN = random.nextInt(6) + 1;
		return lanceN;
	}
}
