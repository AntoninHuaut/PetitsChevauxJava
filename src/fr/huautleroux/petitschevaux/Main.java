package fr.huautleroux.petitschevaux;

import fr.huautleroux.petitschevaux.affichage.Affichage;
import fr.huautleroux.petitschevaux.affichage.Popup;
import fr.huautleroux.petitschevaux.core.GererPartie;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.utils.save.SaveManager;
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

public class Main extends Application {

	private static Main instance;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	private Affichage affichage;
	private Popup popup = new Popup();
	
	private GererPartie petitsChevaux = new GererPartie();
	private SaveManager saveManager = new SaveManager("saves");
	private Scene scene;
	private Stage stage;
	private GridPane infoContenu;

	@Override
	public void start(Stage stage) {
		instance = this;
		this.stage = stage;
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

		for (int i = 1; i <= nbCases; i++)
			for (int j = 1; j <= nbCases; j++) {
				String id = i + "-" + j;
				Rectangle rec = new Rectangle();
				rec.setWidth(recTaille);
				rec.setHeight(recTaille);
				rec.setId(id);

				Couleur c = Couleur.JAUNE;
				if (j<=6 && i<=6) rec.setFill(c.getCaseEcurieColor()); // CaseEcurie
				else if (i==8 && (j>1 && j<8) ) rec.setFill(c.getCaseEchelleColor()); // CaseEchelle
				else if (j<=7 && i<=7 || (i==1 && j==8)) rec.setFill(c.getCaseCheminColor()); // CaseChemin

				c = Couleur.BLEU;
				if (j>=10 && i<=6) rec.setFill(c.getCaseEcurieColor());
				else if (j==8 && (i>1 && i<8)) rec.setFill(c.getCaseEchelleColor());
				else if ((j>=9 && i<=7) || (j==15 && i==8)) rec.setFill(c.getCaseCheminColor());

				c = Couleur.VERT;
				if (j>=10 && i>=10) rec.setFill(c.getCaseEcurieColor());
				else if (i==8 && (j<15 && j>8)) rec.setFill(c.getCaseEchelleColor());
				else if (j>=9 && i>=9 || (j==8 && i==15)) rec.setFill(c.getCaseCheminColor());

				c = Couleur.ROUGE;
				if (j<=6 && i>=10) rec.setFill(c.getCaseEcurieColor());
				else if (j==8 && (i<15 && i>8)) rec.setFill(c.getCaseEchelleColor());
				else if (j<=7 && i>=9 || (j==1 && i==8)) rec.setFill(c.getCaseCheminColor());

				if(j==8 && i==8)  rec.setFill(Color.BLACK);

				Text t = new Text();
				GridPane.setRowIndex(t, i);
				GridPane.setHalignment(t, HPos.CENTER);
				GridPane.setValignment(t, VPos.CENTER);
				GridPane.setColumnIndex(t, j);
				GridPane.setRowIndex(rec, i);
				GridPane.setColumnIndex(rec, j);
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
	
	public Popup getPopup() {
		return popup;
	}
	
	public GererPartie getPetitsChevaux() {
		return petitsChevaux;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Stage getStage() {
		return stage;
	}

	public GridPane getInfoContenu() {
		return infoContenu;
	}
	
	public SaveManager getSaveManager() {
		return saveManager;
	}
	
	public static Affichage getAffStatic() {
		return getInstance().affichage;
	}
	
	public static Popup getPopStatic() {
		return getInstance().popup;
	}
	
	public static Main getInstance() {
		return instance;
	}
}



