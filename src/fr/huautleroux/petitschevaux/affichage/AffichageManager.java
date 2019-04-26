package fr.huautleroux.petitschevaux.affichage;

import java.util.HashMap;
import java.util.List;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class AffichageManager {

	private Main main;
	private HashMap<String, Text> texts = new HashMap<String, Text>();

	public AffichageManager(Main javaFx) {
		this.main = javaFx;
	}

	public void openMenuChargementSauvegarde() {
		supprimerAffichageInfo();
		List<String> sauvegardes = main.getSaveManager().getSauvegardes();

		if (sauvegardes.isEmpty()) {
			main.getPetitsChevaux().demarrerPartie(null);
			return;
		}

		Text labelSauvegarde = new Text("Choisissez une sauvegarde à charger ou lancez une nouvelle partie");
		labelSauvegarde.setId("saves-text");
		ObservableList<String> options = FXCollections.observableArrayList(sauvegardes);

		ComboBox<String> comboBox = new ComboBox<String>(options);
		comboBox.setId("saves-combobox");
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

	public void supprimerAffichageInfo() {
		main.getInfoContenu().getChildren().clear();
	}

	public void addText(String id, Text text) {
		texts.put(id, text);
	}
	
	public void showPopup(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
