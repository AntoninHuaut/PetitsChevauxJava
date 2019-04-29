package fr.huautleroux.petitschevaux.affichage;

import java.util.HashMap;
import java.util.List;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
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
	private Font font = new Font(14);
	private HashMap<String, Text> texts = new HashMap<String, Text>();

	private TextFlow tourActuel = null;

	public Affichage(Main javaFx) {
		this.main = javaFx;
	}

	public void debutTour(int numeroTour) {
		supprimerAffichageInfo();
		tourActuel = new TextFlow();

		Text infoTour = new Text("TOUR N°" + numeroTour);
		infoTour.setFont(new Font(font.getSize() + 4));
		infoTour.setFill(Color.MEDIUMPURPLE);
		tourActuel.getChildren().add(infoTour);

		main.getInfoContenu().getChildren().add(tourActuel);
	}

	public void tirageAuSort(Couleur c, String nomJoueur, Runnable callback) {
		supprimerAffichageInfo();
		TextFlow flow = new TextFlow();
		Text tirage = new Text("Tirage aléatoire : ");
		tirage.setFont(font);
		Text resultat = new Text("C'est " + nomJoueur + " qui commence en premier !");
		resultat.setFont(font);
		Text touche = new Text("\nAppuyer sur Entrer pour continuer");
		touche.setFont(font);
		resultat.setFill(c.getPrincipalColor());
		flow.getChildren().addAll(tirage, resultat, touche);

		attendreToucheEntrer(() -> callback.run());

		main.getInfoContenu().getChildren().add(flow);
	}

	public void openMenuChargementSauvegarde() {
		supprimerAffichageInfo();
		List<String> sauvegardes = main.getSaveManager().getSauvegardes();

		if (sauvegardes.isEmpty()) {
			main.getPetitsChevaux().demarrerPartie(null);
			return;
		}

		Text labelSauvegarde = new Text("Choisissez une sauvegarde à charger ou lancez une nouvelle partie");
		labelSauvegarde.setFont(font);
		ObservableList<String> options = FXCollections.observableArrayList(sauvegardes);

		ComboBox<String> comboBox = new ComboBox<String>(options);
		comboBox.setValue(sauvegardes.size() + " sauvegarde(s) disponible(s)");
		comboBox.setTranslateY(50);
		comboBox.setMaxWidth(280);

		comboBox.setOnAction(e -> {
			if(e.getTarget() instanceof ComboBox) {
				String save = "" + ((ComboBox<?>) e.getTarget()).getValue();

				try {
					Partie partie = main.getSaveManager().chargerPartie(save);
					partie.initialiserReference();
					supprimerAffichageInfo();
					main.getPetitsChevaux().demarrerPartie(partie);
				} catch (ChargementSauvegardeException e1) {
					showPopup(AlertType.ERROR, "Chargement de la sauvegarde", "Le chargement de la sauvegarde " + save + " a échoué");
				}
			}
		});

		Button nouvellePartie = new Button("Nouvelle partie");
		nouvellePartie.setTranslateY(50);
		nouvellePartie.setTranslateX(comboBox.getMaxWidth() + 50);
		nouvellePartie.setOnMouseClicked(e -> {
			supprimerAffichageInfo();
			main.getPetitsChevaux().demarrerPartie(null);
		});

		main.getInfoContenu().getChildren().addAll(labelSauvegarde, comboBox, nouvellePartie);
	} 

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

	public void simpleMessage(String msg, Color couleur) {
		Text simpleMessage = new Text("\n" + msg);
		if (couleur != null) {
			if (couleur.equals(Color.GOLD))
				couleur = Color.ORANGE;

			simpleMessage.setFill(couleur);
		}
		simpleMessage.setFont(font);
		tourActuel.getChildren().add(simpleMessage);
	}

	public void supprimerAffichageInfo() {
		main.getInfoContenu().getChildren().clear();
	}

	public void addText(String id, Text text) {
		texts.put(id, text);
	}
	
	public HashMap<String, Text> getTexts() {
		return texts;
	}

	public void showPopup(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
