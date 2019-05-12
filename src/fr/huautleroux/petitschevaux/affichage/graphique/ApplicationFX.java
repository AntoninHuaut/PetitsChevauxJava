package fr.huautleroux.petitschevaux.affichage.graphique;

import java.util.ArrayList;
import java.util.List;

import fr.huautleroux.petitschevaux.enums.Couleur;
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

public class ApplicationFX extends Application {

	/**
	 * Lance l'application graphique
	 */
	public static void startApplication() {
		Application.launch(ApplicationFX.class, new String [] {});
	}

	private Scene scene;
	private GridPane infoContenu;
	private GridPane root;
	private GridPane grilleContenu;

	private double nbCases = 15;
	private double recTaille = 60;
	private double espacement = 2.5;
	private double marge = 20;

	private double infosWidthTaille = 500;

	private List<Rectangle> rectangles = new ArrayList<Rectangle>();

	/**
	 * Initialisation de l'interface graphique
	 */
	@Override
	public void start(Stage stage) {
		IGraphique affichage = new IGraphique(this);

		root = new GridPane();
		grilleContenu = new GridPane();
		infoContenu = new GridPane();

		resize(stage);

		root.getChildren().addAll(grilleContenu, infoContenu);
		scene = new Scene(root, getTailleCarree() + infosWidthTaille, getTailleCarree());

		for (int y = 0; y < nbCases; y++)
			for (int x = 0; x < nbCases; x++) {
				String id = x + "-" + y;
				Rectangle rec = new Rectangle();
				rec.setWidth(recTaille);
				rec.setHeight(recTaille);
				rectangles.add(rec);

				String c;

				if (y <= 5 && x <= 5) c = Couleur.JAUNE.getEcurieCouleurIG(); // CaseEcurie
				else if (y == 7 && (x > 0 &&  x < 7)) c = Couleur.JAUNE.getEchelleCouleurIG(); // CaseEchelle
				else if (y <= 6 && x <= 6 || (x == 0 && y == 7)) c = Couleur.JAUNE.getCheminCouleurIG(); // CaseChemin

				else if (y <= 5 && x >= 9) c = Couleur.BLEU.getEcurieCouleurIG();
				else if (x == 7 && (y > 0 && y < 7)) c = Couleur.BLEU.getEchelleCouleurIG();
				else if (y <= 6 && x >= 8 || (y == 0 && x == 7)) c = Couleur.BLEU.getCheminCouleurIG();

				else if (y >= 9 && x >= 9) c = Couleur.VERT.getEcurieCouleurIG();
				else if (y == 7 && (x < 14 && x > 7)) c = Couleur.VERT.getEchelleCouleurIG();
				else if (y >= 8 && x >= 8 || (y == 7 && x == 14)) c = Couleur.VERT.getCheminCouleurIG();

				else if (y >= 9 && x <= 5) c = Couleur.ROUGE.getEcurieCouleurIG();
				else if (x == 7 && (y < 14 && y > 7)) c = Couleur.ROUGE.getEchelleCouleurIG();
				else if ((y >= 8 && x <= 6) || (y == 14 && x == 7)) c = Couleur.ROUGE.getCheminCouleurIG();

				else c = "#000000";

				rec.setFill(getCouleurFX(c));

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
		stage.setTitle("Jeu des Petits Chevaux");
		stage.setResizable(true);
		stage.show();

		affichage.openMenuChargementSauvegarde();

		stage.widthProperty().addListener((o, p, newValue) -> {
			if (newValue.doubleValue() - infosWidthTaille <= 0) {
				stage.setWidth(500 + infosWidthTaille);
				stage.setHeight(500);
			}
			else
				stage.setHeight(newValue.doubleValue() - infosWidthTaille);

			resize(stage);
		});

		stage.heightProperty().addListener((o, p, newValue) -> {
			if (newValue.doubleValue() >= 500)
				stage.setWidth(newValue.doubleValue() + infosWidthTaille);
			else {
				stage.setWidth(500 + infosWidthTaille);
				stage.setHeight(500);
			}

			resize(stage);
		});
	}

	private void resize(Stage stage) {		
		recTaille = 60;
		espacement = 2.5;
		marge = 20;

		double taille = stage.getHeight();

		if (!new Double(taille).isNaN()) {
			recTaille = (double) recTaille * taille / 1024.0;
			espacement = (double) espacement * taille / 1024.0 - 0.5;
			marge = (double)  marge * taille / 1024.0;
		}

		grilleContenu.setPadding(new Insets(marge));
		grilleContenu.setHgap(espacement);
		grilleContenu.setVgap(espacement);
		infoContenu.setPadding(new Insets(marge));
		infoContenu.setHgap(espacement);
		infoContenu.setVgap(espacement);

		double tailleCarre = getTailleCarree();
		infoContenu.setTranslateX(tailleCarre);

		if (scene == null)
			return;

		for (Rectangle r : rectangles) {
			r.setWidth(recTaille);
			r.setHeight(recTaille);
		}
	}

	private double getTailleCarree() {
		return (recTaille+espacement)*nbCases + 2*marge;
	}

	public Color getCouleurFX(String couleur) {
		return Color.web(couleur.startsWith("#") ? couleur : "#" + couleur);
	}

	public Scene getScene() {
		return scene;
	}

	public GridPane getInfoContenu() {
		return infoContenu;
	}
}
