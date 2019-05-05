package fr.huautleroux.petitschevaux.affichage;

import java.util.HashMap;
import java.util.List;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.core.GererPartie;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
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

public class Affichage {

	private Main main;
	private Font policeBase = new Font(14);
	private HashMap<String, Text> texts = new HashMap<String, Text>();

	private TextFlow tourActuel = null;

	public Affichage(Main javaFx) {
		this.main = javaFx;
	}

	/**
	 * Affiche le message de début du tour
	 * @param numeroTour Numéro du tour
	 */
	public void debutTour(int numeroTour) {
		supprimerAffichageInfo();
		tourActuel = new TextFlow();

		Text infoTour = new Text("TOUR N°" + numeroTour);
		infoTour.setFont(new Font(policeBase.getSize() + 4));
		infoTour.setFill(Color.MEDIUMPURPLE);
		tourActuel.getChildren().add(infoTour);

		main.getInfoContenu().getChildren().add(tourActuel);
	}

	/**
	 * Affiche le message du tirage au sort et attend une interaction
	 * @param couleur Couleur
	 * @param nomJoueur Nom du joueur qui va jouer
	 * @param callback Bloc à exécuter lorsque l'interaction a été effectuée
	 */
	public void tirageAuSort(Couleur couleur, String nomJoueur, Runnable callback) {
		supprimerAffichageInfo();
		TextFlow flow = new TextFlow();
		Text tirage = new Text("Tirage aléatoire : ");
		tirage.setFont(policeBase);
		Text resultat = new Text("C'est " + nomJoueur + " qui commence en premier !");
		resultat.setFont(policeBase);
		Text touche = new Text("\nAppuyer sur Entrer pour continuer");
		touche.setFont(policeBase);
		resultat.setFill(couleur.getTextCouleur());
		flow.getChildren().addAll(tirage, resultat, touche);

		attendreToucheEntrer(() -> callback.run());

		main.getInfoContenu().getChildren().add(flow);
	}

	/**
	 * Affiche le menu de chargement de sauvegarde
	 */
	public void openMenuChargementSauvegarde() {
		supprimerAffichageInfo();
		List<String> sauvegardes = main.getGestionSauvegarde().getSauvegardes();

		if (sauvegardes.isEmpty()) {
			main.getPetitsChevaux().demarrerPartie(true);
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
					supprimerAffichageInfo();
					
					GererPartie gererPartie = main.getGestionSauvegarde().chargerPartie(sauvegarde);
					gererPartie.demarrerPartie(false);
				} catch (ChargementSauvegardeException e1) {
					main.getPopup().showPopup(AlertType.ERROR, "Chargement de la sauvegarde", null, "Le chargement de la sauvegarde " + sauvegarde + " a échoué");
				}
			}
		});

		Button nouvellePartie = new Button("Nouvelle partie");
		nouvellePartie.setTranslateY(50);
		nouvellePartie.setTranslateX(comboBox.getMaxWidth() + 50);
		nouvellePartie.setOnMouseClicked(e -> {
			supprimerAffichageInfo();
			main.getPetitsChevaux().demarrerPartie(true);
		});

		main.getInfoContenu().getChildren().addAll(labelSauvegarde, comboBox, nouvellePartie);
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

				main.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, this);
				callback.run();
			} 
		};

		main.getScene().addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
	}

	/**
	 * Affiche un message à la suite
	 * @param msg Message
	 * @param couleur Couleur du message (Facultatif)
	 */
	public void simpleMessage(String msg, Color couleur) {
		Text simpleMessage = new Text("\n" + msg);
		if (couleur != null)
			simpleMessage.setFill(couleur);
		
		simpleMessage.setFont(policeBase);
		tourActuel.getChildren().add(simpleMessage);
	}
	
	/**
	 * Affiche le message de fin de partie quand un joueur a gagné
	 * @param numeroTour Numéro du tour en cours
	 * @param joueurGagnant Joueur gagnant
	 */
	public void finDePartie(int numeroTour, Joueur joueurGagnant) {
		supprimerAffichageInfo();
		tourActuel = new TextFlow();
		
		Text infoTour = new Text("FIN DE PARTIE\n\n");
		infoTour.setFont(new Font(policeBase.getSize() + 16));
		infoTour.setFill(Color.MEDIUMPURPLE);

		Text gagnant = new Text(joueurGagnant + " gagne la partie en " + numeroTour + " tours\n\n");
		gagnant.setFont(new Font(policeBase.getSize() + 4));
		gagnant.setFill(joueurGagnant.getCouleur().getTextCouleur());
		
		Button rejouer = new Button("Recommencer une nouvelle partie");
		rejouer.setOnMouseClicked(e -> {
			openMenuChargementSauvegarde();
			
			for (Text text : texts.values())
				text.setText("");
		});
		
		tourActuel.getChildren().addAll(infoTour, gagnant, rejouer);
		main.getInfoContenu().getChildren().add(tourActuel);
	}

	/**
	 * Supprime les messages
	 */
	public void supprimerAffichageInfo() {
		main.getInfoContenu().getChildren().clear();
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
}
