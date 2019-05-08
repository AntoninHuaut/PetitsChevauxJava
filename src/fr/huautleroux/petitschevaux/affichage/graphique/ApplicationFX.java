package fr.huautleroux.petitschevaux.affichage.graphique;

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
	
	public static void startApplication() {
		Application.launch(ApplicationFX.class, new String [] {});
	}
	
	private Scene scene;
	private GridPane infoContenu;

	/**
	 * Initialisation de l'interface graphique
	 */
	@Override
	public void start(Stage stage) {
		IGraphique affichage = new IGraphique(this);

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
		stage.setTitle("Jeu des petits chevaux");
		stage.setResizable(false);
		stage.show();

		affichage.openMenuChargementSauvegarde();
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
