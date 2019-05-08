package fr.huautleroux.petitschevaux.affichage.graphique;

import java.util.HashMap;
import java.util.List;

import fr.huautleroux.petitschevaux.affichage.AffichageInterface;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.GestionPartie;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
import fr.huautleroux.petitschevaux.save.GestionSauvegarde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class IGraphique implements AffichageInterface {
	
	private ApplicationFX applicationFX;
	private GestionSauvegarde gestionSauvegarde = new GestionSauvegarde();
	private Popup popup;

	private Font policeBase = new Font(14);
	private HashMap<String, Text> texts = new HashMap<String, Text>();

	private TextFlow tourActuel = null;

	public IGraphique(ApplicationFX javaFx) {
		this.applicationFX = javaFx;
		this.popup = new Popup(this);
	}

	/**
	 * Affiche le message de début du tour
	 * @param numeroTour Numéro du tour
	 */
	public void debutTour(int numeroTour) {
		effacerAffichage();
		tourActuel = new TextFlow();

		Text infoTour = new Text("TOUR N°" + numeroTour);
		infoTour.setFont(new Font(policeBase.getSize() + 4));
		infoTour.setFill(Color.MEDIUMPURPLE);
		tourActuel.getChildren().add(infoTour);

		applicationFX.getInfoContenu().getChildren().add(tourActuel);
	}

	/**
	 * Affiche le message du tirage au sort et attend une interaction
	 * @param couleur Couleur
	 * @param nomJoueur Nom du joueur qui va jouer
	 * @param callback Bloc à exécuter lorsque l'interaction a été effectuée
	 */
	public void tirageAuSort(Couleur couleur, String nomJoueur, Runnable callback) {
		effacerAffichage();
		TextFlow flow = new TextFlow();
		Text tirage = new Text("Tirage aléatoire : ");
		tirage.setFont(policeBase);
		Text resultat = new Text("C'est " + nomJoueur + " qui commence en premier !");
		resultat.setFont(policeBase);
		Text touche = new Text("\nAppuyer sur Entrer pour continuer");
		touche.setFont(policeBase);
		resultat.setFill(applicationFX.getCouleurFX(couleur.getTextCouleurIG()));
		flow.getChildren().addAll(tirage, resultat, touche);

		attendreToucheEntrer(() -> callback.run());

		applicationFX.getInfoContenu().getChildren().add(flow);
	}

	/**
	 * Affiche le menu de chargement de sauvegarde
	 */
	public void openMenuChargementSauvegarde() {
		effacerAffichage();
		List<String> sauvegardes = gestionSauvegarde.getSauvegardes();

		if (sauvegardes.isEmpty()) {
			new GestionPartie(this).demarrerPartie(true);
			return;
		}

		Text labelSauvegarde = new Text("Choisissez une sauvegarde à charger ou lancez une nouvelle partie");
		labelSauvegarde.setFont(policeBase);
		ObservableList<String> options = FXCollections.observableArrayList(sauvegardes);

		ComboBox<String> comboBox = new ComboBox<String>(options);
		comboBox.setValue(sauvegardes.size() + " sauvegarde(s) disponible(s)");
		comboBox.setTranslateY(50);
		comboBox.setMaxWidth(280);

		comboBox.setOnAction(e -> {
			if(e.getTarget() instanceof ComboBox) {
				String sauvegarde = "" + ((ComboBox<?>) e.getTarget()).getValue();

				try {
					effacerAffichage();

					GestionPartie gererPartie = gestionSauvegarde.chargerPartie(this, sauvegarde);
					gererPartie.demarrerPartie(false);
				} catch (ChargementSauvegardeException e1) {
					getPopup().showPopup(AlertType.ERROR, "Chargement de la sauvegarde", null, "Le chargement de la sauvegarde " + sauvegarde + " a échoué");
				}
			}
		});

		Button nouvellePartie = new Button("Nouvelle partie");
		nouvellePartie.setTranslateY(50);
		nouvellePartie.setTranslateX(comboBox.getMaxWidth() + 50);
		nouvellePartie.setOnMouseClicked(e -> {
			effacerAffichage();
			new GestionPartie(this).demarrerPartie(true);
		});

		applicationFX.getInfoContenu().getChildren().addAll(labelSauvegarde, comboBox, nouvellePartie);
	}

	/**
	 * Exécute un bloc lorsqu'une interaction est effectuée
	 * @param callback Bloc à exécuter lorsque l'interaction a été effectuée
	 */
	public void attendreToucheEntrer(Runnable callback) {
		EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
			@Override 
			public void handle(KeyEvent e) {
				if (!e.getCode().equals(KeyCode.ENTER))
					return;

				applicationFX.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, this);
				callback.run();
			} 
		};

		applicationFX.getScene().addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
	}

	/**
	 * Affiche un message à la suite
	 * @param msg Message
	 * @param couleur Couleur du message (Facultatif)
	 */
	public void simpleMessage(String msg, String couleur) {
		Text simpleMessage = new Text("\n" + msg);
		if (couleur != null)
			simpleMessage.setFill(Color.web(couleur.startsWith("#") ? couleur : "#" + couleur));

		simpleMessage.setFont(policeBase);
		tourActuel.getChildren().add(simpleMessage);
	}

	/**
	 * Affiche le message de fin de partie quand un joueur a gagné
	 * @param numeroTour Numéro du tour en cours
	 * @param joueurGagnant Joueur gagnant
	 */
	public void finDePartie(int numeroTour, Joueur joueurGagnant) {
		effacerAffichage();
		tourActuel = new TextFlow();

		Text infoTour = new Text("FIN DE PARTIE\n\n");
		infoTour.setFont(new Font(policeBase.getSize() + 16));
		infoTour.setFill(Color.MEDIUMPURPLE);

		Text gagnant = new Text(joueurGagnant + " gagne la partie en " + numeroTour + " tours\n\n");
		gagnant.setFont(new Font(policeBase.getSize() + 4));
		gagnant.setFill(applicationFX.getCouleurFX(joueurGagnant.getCouleur().getTextCouleurIG()));

		Button rejouer = new Button("Recommencer une nouvelle partie");
		rejouer.setOnMouseClicked(e -> {
			openMenuChargementSauvegarde();

			for (Text text : texts.values())
				text.setText("");
		});

		tourActuel.getChildren().addAll(infoTour, gagnant, rejouer);
		applicationFX.getInfoContenu().getChildren().add(tourActuel);
	}

	public void miseAJourAffichage(Plateau plateau) {
		for (int y = 0; y < 15; y++)
			for (int x = 0; x < 15; x++) {
				String id = x + "-" + y;

				Text text = texts.get(id);
				text.setText("");

				Case caseCible = plateau.getCaseParCordonnee(x, y);

				if (caseCible == null)
					continue;

				if (caseCible instanceof CaseEcurie) {
					int numeroCheval = 0;
					numeroCheval += x <= 3 ? x%2 : x%11;
					int yTemp = (y <= 3 ? y%2 : y%11);
					numeroCheval += yTemp;

					if (yTemp != 0)
						numeroCheval++;

					Pion p = null;

					for (Pion pGet : caseCible.getChevaux())
						if (pGet.getId() == numeroCheval)
							p = pGet;

					if (p != null)
						text.setText(Couleur.SYMBOL + " " + (p.getId() + 1));
				} else {
					String numeroCases = "";
					Couleur couleur = null;

					for (Pion p : caseCible.getChevaux()) {
						couleur = p.getCouleur();
						numeroCases += (numeroCases.isEmpty() ? Couleur.SYMBOL + " " : ", ") + (p.getId() + 1);
					}

					if (!numeroCases.isEmpty()) {
						text.setText(numeroCases);
						text.setFill(applicationFX.getCouleurFX(couleur.getTextCouleurIG()));
					}
				}
			}

		Text text = texts.get("7-7");
		text.setText(Couleur.SYMBOL);
		text.setFill(Color.WHITE);
		text.setFont(new Font(40));
	}

	/**
	 * Supprime les messages
	 */
	public void effacerAffichage() {
		applicationFX.getInfoContenu().getChildren().clear();
	}

	/**
	 * Ajoute un Text d'une case a un id
	 * @param id Id de la case ('x-y')
	 * @param text Text de la case
	 */
	public void addText(String id, Text text) {
		texts.put(id, text);
	}

	public HashMap<String, Text> getTexts() {
		return texts;
	}

	public Popup getPopup() {
		return popup;
	}

	public ApplicationFX getApplicationFX() {
		return applicationFX;
	}

	public GestionSauvegarde getGestionSauvegarde() {
		return gestionSauvegarde;
	}
}
