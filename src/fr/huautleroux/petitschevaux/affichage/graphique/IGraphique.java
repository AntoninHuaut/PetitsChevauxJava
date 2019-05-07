package fr.huautleroux.petitschevaux.affichage.graphique;

import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.save.GestionSauvegarde;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IGraphique extends Application {
	
	private static IGraphique instance;
	private Affichage affichage;
	private Popup popup = new Popup();

	private GestionSauvegarde gestionSauvegarde = new GestionSauvegarde();
	private Scene scene;
	private GridPane infoContenu;

	/**
	 * Initialisation de l'interface graphique
	 */
	@Override
	public void start(Stage stage) {
		instance = this;
		affichage = new Affichage(this);

		GridPane root = new GridPane();
		GridPane grilleContenu = new GridPane();
		infoContenu = new GridPane();

		double nbCases = 15;
		double recTaille = 60;
		double espacement = 2.5;
		double marge = 20;

		grilleContenu.setPadding(new Insets(marge));
		grilleContenu.setHgap(espacement);
		grilleContenu.setVgap(espacement);
		infoContenu.setPadding(new Insets(marge));
		infoContenu.setHgap(espacement);
		infoContenu.setVgap(espacement);

		double tailleCarre = (recTaille+espacement)*nbCases + 2*marge;
		infoContenu.setTranslateX(tailleCarre);

		root.getChildren().addAll(grilleContenu, infoContenu);
		scene = new Scene(root, tailleCarre + 500, tailleCarre);

		for (int y = 0; y < nbCases; y++)
			for (int x = 0; x < nbCases; x++) {
				String id = x + "-" + y;
				Rectangle rec = new Rectangle();
				rec.setWidth(recTaille);
				rec.setHeight(recTaille);
				rec.setId("rec-" + id);

				Couleur c = Couleur.JAUNE;

				if (y <= 5 && x <= 5) rec.setFill(c.getEcurieCouleurIG()); // CaseEcurie
				else if (y == 7 && (x > 0 &&  x < 7)) rec.setFill(c.getEchelleCouleurIG()); // CaseEchelle
				else if (y <= 6 && x <= 6 || (x == 0 && y == 7)) rec.setFill(c.getCheminCouleurIG()); // CaseChemin

				c = Couleur.ROUGE;
				if (y >= 9 && x <= 5) rec.setFill(c.getEcurieCouleurIG());
				else if (x == 7 && (y < 14 && y > 7)) rec.setFill(c.getEchelleCouleurIG());
				else if ((y >= 8 && x <= 6) || (y == 14 && x == 7)) rec.setFill(c.getCheminCouleurIG());

				c = Couleur.VERT;
				if (y >= 9 && x >= 9) rec.setFill(c.getEcurieCouleurIG());
				else if (y == 7 && (x < 14 && x > 7)) rec.setFill(c.getEchelleCouleurIG());
				else if (y >= 8 && x >= 8 || (y == 7 && x == 14)) rec.setFill(c.getCheminCouleurIG());

				c = Couleur.BLEU;
				if (y <= 5 && x >= 9) rec.setFill(c.getEcurieCouleurIG());
				else if (x == 7 && (y > 0 && y < 7)) rec.setFill(c.getEchelleCouleurIG());
				else if (y <= 6 && x >= 8 || (y == 0 && x == 7)) rec.setFill(c.getCheminCouleurIG());

				if(y == 7 && x == 7)  rec.setFill(Color.BLACK);

				Text t = new Text();
				GridPane.setHalignment(t, HPos.CENTER);
				GridPane.setValignment(t, VPos.CENTER);
				GridPane.setRowIndex(t, y);
				GridPane.setRowIndex(rec, y);
				GridPane.setColumnIndex(t, x);
				GridPane.setColumnIndex(rec, x);
				grilleContenu.getChildren().addAll(rec, t);

				affichage.addText(id, t);
			}

		stage.getIcons().add(new Image(getClass().getResource("/ressources/iconMain.png").toExternalForm()));
		stage.setScene(scene);
		stage.setTitle("Jeu des petits chevaux");
		stage.setResizable(false);
		stage.show();

		affichage.openMenuChargementSauvegarde();
	}

	public Scene getScene() {
		return scene;
	}

	public GridPane getInfoContenu() {
		return infoContenu;
	}

	public GestionSauvegarde getGestionSauvegarde() {
		return gestionSauvegarde;
	}

	public Popup getPopup() {
		return popup;
	}

	public Affichage getAffichage() {
		return affichage;
	}

	public static IGraphique getInstance() {
		return instance;
	}

}
