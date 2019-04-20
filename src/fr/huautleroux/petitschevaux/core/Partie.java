package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Random;

import fr.huautleroux.petitschevaux.PetitsChevaux;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.JoueurBot;
import fr.huautleroux.petitschevaux.entites.JoueurHumain;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class Partie {

	private transient PetitsChevaux main;

	public Partie(PetitsChevaux main) {
		this.main = main;
	}

	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

	private Joueur joueurCourant = null;
	private Plateau plateau = null;
	private Random random = new Random();

	private int nbJoueurHumain;
	private boolean stopPartie = false;

	public void initialiserJeu() {
		int nb;

		do {
			System.out.println("Combien de joueurs vont participer ?");
			nb = Saisie.asInt();
		} while (nb > 4 || nb < 1);

		initialiserJoueurs(nb);
		initialiserPlateau();
		startJeu();
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
			joueurs.add(new JoueurBot( couleurs[nbJoueurHumain + i]));
	}

	public void initialiserPlateau() {
		this.plateau = new Plateau(this);

		for(int idJoueur = 0; idJoueur < joueurs.size(); idJoueur++) {
			Joueur j = joueurs.get(idJoueur);
			j.setCaseDeDepart(plateau.getChemin().get(idJoueur * 14));

			for(int idCheval = 0; idCheval < 4; idCheval++) {
				Pion pion = new Pion(idCheval, Couleur.values()[idJoueur]);
				j.addCheval(pion);
				plateau.getEcuries().get(idJoueur).ajouteCheval(pion);
			}
		}
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

			int de = lancerDe();
			int choixAction = joueurCourant.choixAction(de, plateau);

			if (choixAction == 3) {
				if(menuSauvegarde())
					return;
			}

			else if (choixAction == 1 || choixAction == 2) {
				Pion pion = joueurCourant.choisirPion(de, choixAction, plateau);
				
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
		boolean overwrite = false;

		if (main.getSaveManager().estSauvegardeValide(nomSauvegarde)) {
			System.out.println("Une sauvegarde existe avec ce nom, souhaitez-vous l'écraser ? (Oui/Non)");
			overwrite = Saisie.asBoolean();
		}

		try {
			main.getSaveManager().sauvegarderPartie(this, nomSauvegarde, overwrite);
			System.out.println("La partie a été sauvegarde sur le slot " + nomSauvegarde);

			System.out.println("Souhaitez-vous quitter la partie en cours ? (Oui/Non)");
			stopPartie = Saisie.asBoolean();
		} catch (SauvegardeException e) {
			System.err.println("La sauvegarde n'a pas pu s'effectuer : " + e.getMessage());
		}

		return stopPartie;
	}

	public boolean estPartieTerminee() {
		return false;
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

	public ArrayList<Joueur> getJoueurs() {
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
