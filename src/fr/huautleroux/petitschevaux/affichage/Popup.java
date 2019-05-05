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

	/**
	 * Ouvre le menu de sauvegarde
	 * @param partie Partie à sauvegarder
	 * @return Résultat de la sauvegarde
	 * @throws SauvegardeException Erreur générée si la sauvegarde a échoué
	 */
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
		TextField textField = dialog.getEditor();
		textField.textProperty().addListener((o) -> okButton.setDisable(textField.getText().trim().isEmpty()));
		okButton.setDisable(true);
		textField.requestFocus();

		Optional<String> optionalSauvegarde = dialog.showAndWait();

		if (!optionalSauvegarde.isPresent())
			return SauvegardeResultat.ANNULER;

		String nomSauvegarde = optionalSauvegarde.get();
		nomSauvegarde = Main.getInstance().getGestionSauvegarde().convertSaveName(nomSauvegarde);
		boolean ecraserSauvegarde = false;

		if (Main.getInstance().getGestionSauvegarde().estSauvegardeValide(nomSauvegarde)) {
			BooleanResultat booleanResultat = getBooleanSauvegarde("Menu Sauvegarde", "Une sauvegarde sous le nom de " + nomSauvegarde + " existe déjà", "Souhaitez-vous l'écraser ?");
			
			if (booleanResultat.equals(BooleanResultat.ANNULER))
				return SauvegardeResultat.ANNULER;
			
			ecraserSauvegarde = booleanResultat.equals(BooleanResultat.OUI);
		}

		Main.getInstance().getGestionSauvegarde().sauvegarderPartie(partie, nomSauvegarde, ecraserSauvegarde);
		BooleanResultat booleanResultat = getBooleanSauvegarde("Menu Sauvegarde", "La sauvegarde s'est terminée avec succès", "Souhaitez-vous quitter la partie en cours ?");
		
		if (booleanResultat.equals(BooleanResultat.ANNULER))
			return SauvegardeResultat.ANNULER;
		
		boolean quitter = booleanResultat.equals(BooleanResultat.OUI);
		return quitter ? SauvegardeResultat.QUITTER : SauvegardeResultat.CONTINUER;
	}

	/**
	 * Fenêtre demandant un choix Oui/Non
	 * @param title Nom de la fenêtre
	 * @param header Header de la fenêtre
	 * @param content Message de la fenêtre
	 * @return Résultat choisi par l'utilisateur
	 */
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

		Optional<ButtonType> optionalBouton = dialog.showAndWait();

		if (!optionalBouton.isPresent())
			return BooleanResultat.ANNULER;

		return optionalBouton.get().equals(buttonOui) ? BooleanResultat.OUI : BooleanResultat.NON;
	}

	/**
	 * Fenêtre affichant les actions disponibles pour un joueur
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 * @param actionsDispo Liste des actions disponibles pour le joueur
	 * @param actionDefaut Action par défaut
	 * @param joueur Joueur
	 * @return Action choisie par le joueur
	 */
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
		
		Optional<String> optionalJoueurAction = dialog.showAndWait();
		if (!optionalJoueurAction.isPresent())
			return getJoueurAction(de, actionsDispo, actionDefaut, joueur);
		else
			return getJoueurActionByNom(optionalJoueurAction.get());
	}

	/**
	 * Fenêtre affichant les pions disponibles pour l'action choisie par un joueur
	 * @param action Action choisie par le joueur
	 * @param pionsDispo Liste des pions disponibles
	 * @param joueur Joueur
	 * @return Pion choisie par le joueur
	 */
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
		
		Optional<String> optionalPion = dialog.showAndWait();
		if (!optionalPion.isPresent())
			return getJoueurPion(action, pionsDispo, joueur);
		else
			return getJoueurPionByNom(joueur, optionalPion.get());
	}

	/**
	 * Fenêtre demandant un nombre à l'utilisateur
	 * @param nombreMax Nombre max a ne pas dépasser
	 * @param title Titre de la fenêtre
	 * @param header Header de la fenêtre
	 * @param message Message de la fenêtre
	 * @return Nombre entier entre 0 et nombreMax
	 */
	public Integer getNombres(int nombreMax, String title, String header, String content) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initModality(Modality.WINDOW_MODAL);
		setIcon(dialog, "iconConfiguration");
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);

		Button buttonOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		buttonOk.setText("Valider");
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setManaged(false);
		TextField textField = dialog.getEditor();
		textField.textProperty().addListener((o) -> {
			boolean desactiver = true;

			try {
				int valeur = Integer.parseInt(textField.getText().trim());
				desactiver = valeur < 0 || valeur > nombreMax;
			} catch (NumberFormatException e) {}

			buttonOk.setDisable(desactiver);
			textField.setStyle("-fx-text-inner-color: " + (desactiver ? MAUVAISE_ENTREE : BONNE_ENTREE) + ";");
		});
		
		buttonOk.setDisable(true);
		textField.requestFocus();

		Optional<String> optionalNombreJoueurs = dialog.showAndWait();

		if (!optionalNombreJoueurs.isPresent())
			return getNombres(nombreMax, title, header, content);
		return Integer.parseInt(optionalNombreJoueurs.get());
	}

	/**
	 * Fenêtre permettant la configuration des joueurs (Pseudo + Couleur)
	 * @param nbJoueur Nombre de joueurs humains
	 * @return HashMap associant le pseudo de chaque joueur à sa couleur
	 */
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

		for (TextField textField : pairs.keySet()) {
			textField.textProperty().addListener((o) -> checkInitialisationJoueurs(pairs, validerButton));
			pairs.get(textField).textProperty().addListener((o) -> checkInitialisationJoueurs(pairs, validerButton));
		}

		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == validerButtonType) {
				HashMap<String, Couleur> resultats = new HashMap<String, Couleur>();

				for (TextField textField : pairs.keySet())
					resultats.put(textField.getText(), getCouleurString(pairs.get(textField).getText()));

				return resultats;
			}

			return null;
		});

		Optional<HashMap<String, Couleur>> optionalPairs = dialog.showAndWait();

		if (!optionalPairs.isPresent())
			return getInitialisationJoueurs(nbJoueur);
		return optionalPairs.get();
	}

	/**
	 * Affiche une fenêtre d'information
	 * @param type Type de la fenêtre
	 * @param title Titre de la fenêtre
	 * @param header Header de la fenêtre
	 * @param message Message de la fenêtre
	 */
	public void showPopup(AlertType type, String title, String header, String message) {
		Alert dialog = new Alert(type);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(message);
		dialog.showAndWait();
	}

	/**
	 * Vérifie que les valeurs entrées par l'utilisateur sont correctes
	 * @param pairs HashMap associant le pseudo de chaque joueur à sa couleur
	 * @param validerButton Bouton permettant la validation des données
	 */
	private void checkInitialisationJoueurs(HashMap<TextField, TextField> pairs, Node validerButton) {
		List<Couleur> couleursUsed = new ArrayList<Couleur>();
		List<String> pseudo = new ArrayList<String>();
		boolean desactiver = false;

		for (TextField tfSub : pairs.keySet()) {
			String pseudoSub = tfSub.getText().trim();
			pseudo.add(pseudoSub);
			String couleurSub = pairs.get(tfSub).getText().trim();

			desactiver = pseudoSub.isEmpty() || couleurSub.isEmpty() ? true : desactiver;

			Couleur c = getCouleurString(couleurSub);

			if (c == null)
				desactiver = true;
			else
				couleursUsed.add(c);

			tfSub.setStyle("-fx-text-inner-color: " + (pseudoSub.isEmpty() ? MAUVAISE_ENTREE : BONNE_ENTREE) + ";");
			pairs.get(tfSub).setStyle("-fx-text-inner-color: " + (c == null ? MAUVAISE_ENTREE : BONNE_ENTREE) + ";");
		}

		Set<Couleur> couleurSansDouble = new HashSet<Couleur>(couleursUsed);
		Set<String> pseudoSansDouble = new HashSet<String>(pseudo);

		if (couleurSansDouble.size() != couleursUsed.size()) {
			desactiver = true;

			for (TextField tfValue : pairs.values()) {
				Couleur couleur = getCouleurString(tfValue.getText().trim());

				if (couleur != null && Collections.frequency(couleursUsed, couleur) > 1)
					tfValue.setStyle("-fx-text-inner-color: " + MAUVAISE_ENTREE + ";");
			}
		}

		if (pseudoSansDouble.size() != pseudo.size()) {
			desactiver = true;

			for (TextField tfSub : pairs.keySet())
				if (Collections.frequency(pseudo, tfSub.getText().trim()) > 1)
					tfSub.setStyle("-fx-text-inner-color: " + MAUVAISE_ENTREE + ";");
		}

		validerButton.setDisable(desactiver);
	}

	/**
	 * Retourne le pion d'un joueur par son nom
	 * @param joueur Joueur
	 * @param pionStr Nom du pion
	 * @return Pion
	 */
	private Pion getJoueurPionByNom(Joueur joueur, String pionStr) {
		for (Pion pion : joueur.getChevaux())
			if (pion.toString().equals(pionStr))
				return pion;

		return null;
	}

	/**
	 * Retourne l'action par son nom
	 * @param actionStr Nom de l'action
	 * @return Action d'un joueur
	 */
	private JoueurAction getJoueurActionByNom(String actionStr) {
		for (JoueurAction action : JoueurAction.values())
			if (action.getNom().equals(actionStr))
				return action;

		return JoueurAction.RIEN_FAIRE;
	}

	/**
	 * Retourne la couleur par son nom
	 * @param couleurStr Nom de la couleur
	 * @return Couleur
	 */
	private Couleur getCouleurString(String couleurStr) {
		if (couleurStr.isEmpty())
			return null;

		for (Couleur couleur : Couleur.values())
			if ((couleur.name().toLowerCase()).startsWith(couleurStr.toLowerCase()))
				return couleur;

		return null;
	}
	
	/**
	 * Définie l'icône d'une fenêtre
	 * @param dialog Fenêtre
	 * @param nom Nom de l'icone
	 */
	private void setIcon(Dialog<?> dialog, String nom) {
		((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResource("/ressources/" + nom + ".png").toExternalForm()));
	}
}
