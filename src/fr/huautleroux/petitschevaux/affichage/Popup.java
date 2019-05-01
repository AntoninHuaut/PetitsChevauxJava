package fr.huautleroux.petitschevaux.affichage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.BooleanResultat;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.enums.SauvegardeResultat;
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

	private final String MAUVAISE_ENTREE = "red";
	private final String BONNE_ENTREE = "green";

	public SauvegardeResultat menuSauvegarde(Partie partie) throws SauvegardeException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconSauvegarde");
		dialog.setTitle("Menu Sauvegarde");
		dialog.setHeaderText(null);
		dialog.setContentText("Entrez le nom de la sauvegarde souhaitée : ");

		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setText("Valider");
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setManaged(false);
		TextField tf = dialog.getEditor();
		tf.textProperty().addListener((observable) -> okButton.setDisable(tf.getText().trim().isEmpty()));
		okButton.setDisable(true);
		tf.requestFocus();

		Optional<String> optionalSauvegarde = dialog.showAndWait();

		if (!optionalSauvegarde.isPresent())
			return SauvegardeResultat.ANNULER;

		String nomSauvegarde = optionalSauvegarde.get();
		nomSauvegarde = Main.getInstance().getSaveManager().convertSaveName(nomSauvegarde);
		boolean overwrite = false;

		if (Main.getInstance().getSaveManager().estSauvegardeValide(nomSauvegarde)) {
			BooleanResultat booleanResultat = getBooleanSauvegarde("Menu Sauvegarde", "Une sauvegarde sous le nom de " + nomSauvegarde + " existe déjà", "Souhaitez-vous l'écraser ?");
			if (booleanResultat.equals(BooleanResultat.ANNULER))
				return SauvegardeResultat.ANNULER;
			overwrite = booleanResultat.equals(BooleanResultat.OUI);
		}

		Main.getInstance().getSaveManager().sauvegarderPartie(partie, nomSauvegarde, overwrite);
		BooleanResultat booleanResultat = getBooleanSauvegarde("Menu Sauvegarde", "La sauvegarde s'est terminée avec succès", "Souhaitez-vous quitter la partie en cours ?");
		if (booleanResultat.equals(BooleanResultat.ANNULER))
			return SauvegardeResultat.ANNULER;
		boolean quitter = booleanResultat.equals(BooleanResultat.OUI);

		return quitter ? SauvegardeResultat.QUITTER : SauvegardeResultat.CONTINUER;
	}

	public BooleanResultat getBooleanSauvegarde(String title, String header, String content) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconSauvegarde");
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);

		ButtonType buttonOui = new ButtonType("Oui");
		ButtonType buttonNon = new ButtonType("Non");
		dialog.getButtonTypes().setAll(buttonOui, buttonNon);

		Optional<ButtonType> result = dialog.showAndWait();

		if (!result.isPresent())
			return BooleanResultat.ANNULER;

		return result.get().equals(buttonOui) ? BooleanResultat.OUI : BooleanResultat.NON;
	}

	public JoueurAction getJoueurAction(int de, List<JoueurAction> actionsDispo, JoueurAction actionDefaut, Joueur joueur) {
		List<String> actionsDispoStr = new ArrayList<String>();
		actionsDispo.forEach(action -> actionsDispoStr.add(action.getNom()));
		ChoiceDialog<String> dialog = new ChoiceDialog<>(actionDefaut.getNom(), actionsDispoStr);
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconAutre");
		String joueurInfos = joueur.getNom() + " (" + joueur.getCouleur() + ")";
		dialog.setTitle("Au tour de " + joueurInfos);
		dialog.setHeaderText(joueurInfos + "\nVous avez fait " + de);
		dialog.setContentText("Choisissez une action");	
		Optional<String> optional = dialog.showAndWait();
		if (!optional.isPresent())
			return getJoueurAction(de, actionsDispo, actionDefaut, joueur);
		else
			return getJoueurActionByNom(optional.get());
	}

	public Pion getJoueurPion(JoueurAction action, List<Pion> pionsDispo, Joueur joueur) {
		List<String> pionsDispoStr = new ArrayList<String>();
		pionsDispo.forEach(pion -> pionsDispoStr.add(pion.toString()));
		ChoiceDialog<String> dialog = new ChoiceDialog<>(pionsDispoStr.get(0), pionsDispoStr);
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconAutre");
		String joueurInfos = joueur.getNom() + " (" + joueur.getCouleur() + ")";
		dialog.setTitle("Au tour de " + joueurInfos);
		dialog.setHeaderText(joueurInfos + "\nQuel pion choisir pour l'action : " + action.getNom());
		dialog.setContentText("Choisissez un pion");
		Optional<String> optional = dialog.showAndWait();
		if (!optional.isPresent())
			return getJoueurPion(action, pionsDispo, joueur);
		else
			return getJoueurPionByNom(joueur, optional.get());
	}

	public Integer getNombresJoueurs() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconConfiguration");
		dialog.setTitle("Nouvelle partie : Configuration");
		dialog.setHeaderText("Le nombre de joueurs doit être compris en 0 et 4");
		dialog.setContentText("Nombre de joueurs : ");

		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setText("Valider");
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setManaged(false);
		TextField tf = dialog.getEditor();
		tf.textProperty().addListener((observable) -> {
			boolean disabled = true;

			try {
				int get = Integer.parseInt(tf.getText().trim());
				disabled = get < 0 || get > 4;
			} catch (NumberFormatException e) {}

			okButton.setDisable(disabled);
			tf.setStyle("-fx-text-inner-color: " + (disabled ? MAUVAISE_ENTREE : BONNE_ENTREE) + ";");
		});
		okButton.setDisable(true);
		tf.requestFocus();

		Optional<String> optional = dialog.showAndWait();

		if (!optional.isPresent())
			return getNombresJoueurs();
		return Integer.parseInt(optional.get());
	}

	public HashMap<String, Couleur> getInitialisationJoueurs(int nbJoueur) {
		Dialog<HashMap<String, Couleur>> dialog = new Dialog<>();
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconConfiguration");
		dialog.setTitle("Nouvelle partie : Configuration");
		dialog.setHeaderText("Veuillez entrer le pseudo unique et sa couleur unique (Jaune/Bleu/Rouge/Vert) de chaque joueur");

		ButtonType validerButtonType = new ButtonType("Valider", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(validerButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		HashMap<TextField, TextField> pairs = new HashMap<TextField, TextField>();

		for (int i = 0; i < nbJoueur; i++) {
			TextField nomJoueur = new TextField();
			TextField couleurJoueur = new TextField();

			grid.add(new Label("Nom du joueur :"), 0, i);
			grid.add(nomJoueur, 1, i);
			grid.add(new Label("Couleur : "), 2, i);
			grid.add(couleurJoueur, 3, i);

			pairs.put(nomJoueur, couleurJoueur);

			if (i == 0)
				nomJoueur.requestFocus();
		}

		Node validerButton = dialog.getDialogPane().lookupButton(validerButtonType);
		validerButton.setDisable(true);

		for (TextField tf : pairs.keySet()) {
			tf.textProperty().addListener((observable) -> checkInitJoueurs(pairs, validerButton));
			pairs.get(tf).textProperty().addListener((observable) -> checkInitJoueurs(pairs, validerButton));
		}

		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == validerButtonType) {
				HashMap<String, Couleur> resultats = new HashMap<String, Couleur>();

				for (TextField tf : pairs.keySet())
					resultats.put(tf.getText(), getCouleurString(pairs.get(tf).getText()));

				return resultats;
			}

			return null;
		});

		Optional<HashMap<String, Couleur>> optional = dialog.showAndWait();

		if (!optional.isPresent())
			return getInitialisationJoueurs(nbJoueur);
		return optional.get();
	}

	public void showPopup(AlertType type, String title, String header, String message) {
		Alert dialog = new Alert(type);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(message);
		dialog.showAndWait();
	}

	private void checkInitJoueurs(HashMap<TextField, TextField> pairs, Node validerButton) {
		List<Couleur> couleursUsed = new ArrayList<Couleur>();
		List<String> pseudo = new ArrayList<String>();
		boolean disable = false;

		for (TextField tfSub : pairs.keySet()) {
			String pseudoSub = tfSub.getText().trim();
			pseudo.add(pseudoSub);
			String couleurSub = pairs.get(tfSub).getText().trim();

			disable = pseudoSub.isEmpty() || couleurSub.isEmpty() ? true : disable;

			Couleur c = getCouleurString(couleurSub);

			if (c == null)
				disable = true;
			else
				couleursUsed.add(c);

			tfSub.setStyle("-fx-text-inner-color: " + (pseudoSub.isEmpty() ? MAUVAISE_ENTREE : BONNE_ENTREE) + ";");
			pairs.get(tfSub).setStyle("-fx-text-inner-color: " + (c == null ? MAUVAISE_ENTREE : BONNE_ENTREE) + ";");
		}

		Set<Couleur> couleurSansDouble = new HashSet<Couleur>(couleursUsed);
		Set<String> pseudoSansDouble = new HashSet<String>(pseudo);

		if (couleurSansDouble.size() != couleursUsed.size()) {
			disable = true;

			for (TextField tfValue : pairs.values()) {
				Couleur c = getCouleurString(tfValue.getText().trim());

				if (c != null && Collections.frequency(couleursUsed, c) > 1)
					tfValue.setStyle("-fx-text-inner-color: " + MAUVAISE_ENTREE + ";");
			}
		}

		if (pseudoSansDouble.size() != pseudo.size()) {
			disable = true;

			for (TextField tfSub : pairs.keySet())
				if (Collections.frequency(pseudo, tfSub.getText().trim()) > 1)
					tfSub.setStyle("-fx-text-inner-color: " + MAUVAISE_ENTREE + ";");
		}

		validerButton.setDisable(disable);
	}

	private Pion getJoueurPionByNom(Joueur joueur, String pionStr) {
		for (Pion pion : joueur.getChevaux())
			if (pion.toString().equals(pionStr))
				return pion;

		return null;
	}

	private JoueurAction getJoueurActionByNom(String actionStr) {
		for (JoueurAction action : JoueurAction.values())
			if (action.getNom().equals(actionStr))
				return action;

		return JoueurAction.RIEN_FAIRE;
	}

	private Couleur getCouleurString(String couleurStr) {
		if (couleurStr.isEmpty())
			return null;

		for (Couleur couleur : Couleur.values())
			if ((couleur.name().toLowerCase()).startsWith(couleurStr.toLowerCase()))
				return couleur;

		return null;
	}
	
	private void setIcon(Dialog<?> dialog, String nom) {
		((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResource("/ressources/" + nom + ".png").toExternalForm()));
	}
}
