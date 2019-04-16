package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;

import java.util.Random;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.JoueurHumain;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class Partie {

	private Joueur joueurCourant;
	private ArrayList<Joueur> joueurs;
	private ArrayList<Pion> pions;
	private Random random = new Random();
	private Plateau plateau;

	public Partie() {
		this.joueurs = new ArrayList<Joueur>();
		this.plateau = null;
		int nb;
		
		do {
			System.out.println("Combien de joueurs vont participer ?");
			nb = Saisie.asInt();
		} while (nb > 4 || nb < 1);

		initialiserJoueurs(nb);
		initialiserPlateau();
		
		while(!estPartieTerminee()) {
			jouerUnTour(nb);
			
		}
	}

	public void initialiserJoueurs(int nb){
		Couleur[] couleurs = Couleur.values();
		int id = 0;
		for(int i = 0; i < nb; i++) {
			String nom;
			System.out.println("Entrez votre pseudo");
			nom = Saisie.asString();
			joueurs.add(new JoueurHumain(nom, couleurs[i]));
			for(int j = 0; j<4;j++)	{		
				pions.add(new Pion(id,couleurs[i]));
				id += 1;
			}	
		}
	}

	public void initialiserPlateau() {
		this.plateau = new Plateau();
	}

	public void jouerUnTour(int nb) {
		int de;
		Pion p;
		for (int i =0; i<nb;i++) {
			setJoueurCourant(joueurs.get(i));
			de = lancerDe();
			p = choisirPion(de, plateau);
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
		int lanceN = random.nextInt((6-1)+1)+1;
		return lanceN;
	}

}
