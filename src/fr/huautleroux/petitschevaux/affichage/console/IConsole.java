package fr.huautleroux.petitschevaux.affichage.console;

import java.io.IOException;
import java.util.HashMap;

import fr.huautleroux.petitschevaux.affichage.AffichageInterface;
import fr.huautleroux.petitschevaux.core.GererPartie;
import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.SauvegardeResultat;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;
import fr.huautleroux.petitschevaux.save.GestionSauvegarde;
import javafx.scene.paint.Color;

public class IConsole implements AffichageInterface {

	private GestionSauvegarde gestionSauvegarde = new GestionSauvegarde();

	public void start() {
		effacerAffichage();

		GererPartie partie;
		boolean nouvellePartie = true;

		try {
			partie = menuChargementSauvegarde();
			nouvellePartie = false;
		} catch (ChargementSauvegardeException e) {
			partie = new GererPartie();
		}

		partie.demarrerPartie(nouvellePartie);
	}

	public void effacerAffichage() {
		String os = System.getProperty("os.name");

		try {
			if (os.contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				System.out.print("\033[H\033[2J");
		} catch(IOException | InterruptedException e) {}
	}

	public void debutTour(int numeroTour) {
		System.out.println(Utils.PURPLE_BRIGHT + "TOUR N°" + numeroTour + Utils.RESET);
	}

	public int getNombreJoueurs() {
		int nbJoueur;

		do {
			System.out.print("Entrez le nombre de joueurs qui vont participer : ");
			nbJoueur = Saisie.asInt();
			System.out.println("");
		} while (nbJoueur > 4 || nbJoueur < 0);

		return nbJoueur;
	}

	public int getDeTruque(int de) {
		int deTruque;

		System.out.println("Dé original : " + de);

		do {
			System.out.print("Entrez la valeur du dé truquée : ");
			deTruque = Saisie.asInt();
			System.out.println("");
		} while (deTruque < 0 || deTruque > 999);

		return deTruque;
	}

	public SauvegardeResultat menuSauvegarde(Partie partie) throws SauvegardeException {
		System.out.println("Entrez le nom souhaité pour la sauvegarde");
		String nomSauvegarde = Saisie.asStringNoEmpty();
		nomSauvegarde = gestionSauvegarde.convertSaveName(nomSauvegarde);
		boolean overwrite = false;

		if (gestionSauvegarde.estSauvegardeValide(nomSauvegarde)) {
			System.out.println("Une sauvegarde existe avec ce nom, souhaitez-vous l'écraser ? (Oui/Non)");
			overwrite = Saisie.asBoolean();
		}

		gestionSauvegarde.sauvegarderPartie(partie, nomSauvegarde, overwrite);
		System.out.println("La partie a été sauvegarde sur le slot " + nomSauvegarde);

		System.out.println("Souhaitez-vous quitter la partie en cours ? (Oui/Non)");
		boolean stopPartie = Saisie.asBoolean();

		return stopPartie ? SauvegardeResultat.QUITTER : SauvegardeResultat.CONTINUER;
	}

	public GererPartie menuChargementSauvegarde() throws ChargementSauvegardeException {
		if(gestionSauvegarde.getSauvegardes().isEmpty())
			throw new ChargementSauvegardeException("Aucune sauvegarde n'existe");

		System.out.println("Souhaitez-vous charger une sauvegarde ? (Oui/Non)");
		boolean chargerSauvegarde = Saisie.asBoolean();

		if(!chargerSauvegarde)
			throw new ChargementSauvegardeException("Opération interrompue");

		System.out.println(" Liste des sauvegardes :");
		gestionSauvegarde.getSauvegardes().forEach(save -> System.out.println("    • " + save));

		String nomSauvegarde;

		do {
			System.out.print("Choisissez la sauvegarde à charger (Tappez stop pour annuler) : ");
			nomSauvegarde = Saisie.asStringNoEmpty();
			System.out.println("");
		} while(!gestionSauvegarde.estSauvegardeValide(nomSauvegarde) && !nomSauvegarde.equals("stop"));

		if(nomSauvegarde.equals("stop"))
			throw new ChargementSauvegardeException("Opération interrompue");

		nomSauvegarde = gestionSauvegarde.convertSaveName(nomSauvegarde);

		GererPartie gererPartie = gestionSauvegarde.chargerPartie(nomSauvegarde);
		System.out.println("La partie " + nomSauvegarde + " a été chargée\n");
		return gererPartie;
	}

	public void tirageAuSort(Couleur couleur, String nomJoueur, Runnable callback) {
		System.out.println(couleur.getTextCouleurIC() + "Tirage aléatoire : ");
		System.out.println("C'est " + nomJoueur + " qui commence en premier !" + Utils.RESET);
		System.out.println("\nAppuyer sur Entrer pour continuer");

		attendreToucheEntrer(() -> callback.run());
	}

	public void attendreToucheEntrer(Runnable callback) {
		Saisie.asString();
		callback.run();
	}

	public void simpleMessage(String msg, Color color) {
		System.out.println(msg);
	}

	public void finDePartie(int numeroTour, Joueur joueurGagnant) {
		System.out.println(Utils.PURPLE_BRIGHT + "FIN DE PARTIE\n\n" + Utils.RESET);
		System.out.println(joueurGagnant.getCouleur().getTextCouleurIC() + joueurGagnant + " gagne la partie en " + numeroTour + " tours\n\n" + Utils.RESET);
		System.out.println("\nAppuyer sur Entrer pour relancer une partie");

		attendreToucheEntrer(() -> start());
	}

	public HashMap<String, Couleur> getInitialisationJoueurs(int nbJoueurHumain) {
		HashMap<String, Couleur> joueurs = new HashMap<String, Couleur>();
		System.out.println("Couleur : J(aune) / B(leu) / V(ert) / R(ouge)");

		for (int i = 0; i < nbJoueurHumain; i++) {
			System.out.println("Nouveau Joueur");

			String pseudo;

			do {
				System.out.print("  Entrez votre pseudo : ");
				pseudo = Saisie.asStringNoEmpty();
			} while (joueurs.containsKey(pseudo));
			
			Couleur couleur;
			
			do {
				System.out.print("  Entrez la couleur que vous souhaitez : ");
				couleur = Saisie.asCouleur();
			} while (joueurs.containsValue(couleur));
			
			System.out.println("");
			joueurs.put(pseudo, couleur);
		}

		return joueurs;
	}
}
