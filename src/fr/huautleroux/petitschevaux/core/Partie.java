package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;

import java.util.Random;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.JoueurBot;
import fr.huautleroux.petitschevaux.entites.JoueurHumain;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class Partie {

	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

	private Joueur joueurCourant = null;
	private Plateau plateau = null;
	private Random random = new Random();

	private int nbJoueurHumain;

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
			System.out.println("Entrez votre pseudo");
			String nom = Saisie.asString();
			joueurs.add(new JoueurHumain(nom, couleurs[i]));
		}
		
		for(int i = 0; i < (4 - nbJoueurHumain); i++)
			joueurs.add(new JoueurBot(couleurs[nbJoueurHumain + i]));
	}

	public void initialiserPlateau() {
		this.plateau = new Plateau();

		for(int i = 0; i < joueurs.size(); i++)
			joueurs.get(i).setCaseDeDepart(plateau.getEcuries().get(i));
	}

	public void startJeu() {
		while(!estPartieTerminee()) {
			jouerUnTour();

			break; // Evitez boucle infini pour les tests
		}
	}

	public void jouerUnTour() {
		for (int i = 0; i < joueurs.size(); i++) {
			setJoueurCourant(joueurs.get(i));
			int de = lancerDe();
			Pion pion = joueurCourant.choisirPion(de, plateau);
			Case caseA = pion.getCaseActuelle(plateau);
			Case caseCible = pion.getCaseCible(plateau, caseA, de);
			plateau.deplacerPionA(pion, caseCible);
		}
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

	private void mangerLesPions(Case caseCible) {

	}

	private int lancerDe() {
		int lanceN = random.nextInt(7);
		return lanceN;
	}

}
