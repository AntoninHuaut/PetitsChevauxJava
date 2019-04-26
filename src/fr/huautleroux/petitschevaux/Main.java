package fr.huautleroux.petitschevaux;

import fr.huautleroux.petitschevaux.affichage.AffichageManager;
import fr.huautleroux.petitschevaux.affichage.GererPartie;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.utils.save.SaveManager;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
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

	private GererPartie petitsChevaux = new GererPartie();
	private SaveManager saveManager = new SaveManager("saves");
	private AffichageManager affichageManager;
	private Scene scene;
	private Stage stage;
	private GridPane infoContenu;

	@Override
	public void start(Stage stage) {
		instance = this;
		this.stage = stage;
		affichageManager = new AffichageManager(this);
		
		GridPane root = new GridPane();
		GridPane grilleContenu = new GridPane();
		infoContenu = new GridPane();

		int length = 15;
		int recTaille = 50;
		int espacement = 5;
		int padding = 20;

		grilleContenu.setPadding(new Insets(padding));
		grilleContenu.setHgap(espacement);
		grilleContenu.setVgap(espacement);
		infoContenu.setPadding(new Insets(padding));
		infoContenu.setHgap(espacement);
		infoContenu.setVgap(espacement);

		int tailleCarre = (recTaille-7)*(espacement+length);
		infoContenu.setTranslateX(tailleCarre);
		
		root.getChildren().addAll(grilleContenu, infoContenu);
		scene = new Scene(root, tailleCarre + 500, tailleCarre);

		for (int i = 1; i < length + 1; i++)
			for (int j = 1; j < length + 1; j++) {
				String id = i + "-" + j;
				Rectangle rec = new Rectangle();
				rec.setWidth(recTaille);
				rec.setHeight(recTaille);
				rec.setId(id);

				if (j<=6 && i<=6)  rec.setFill(Couleur.BLEU.getCaseEcurieColor()); // CaseEcurie
				else if (i==8 && (j>1 && j<8) ) rec.setFill(Couleur.BLEU.getCaseEchelleColor()); // CaseEchelle
				else if (j<=7 && i<=7  ||  (i==1 && j==8)) rec.setFill(Couleur.BLEU.getCaseCheminColor()); // CaseChemin

				if (j>=10 && i<=6)  rec.setFill(Couleur.ROUGE.getCaseEcurieColor());
				else if (j==8 && (i>1 && i<8)) rec.setFill(Couleur.ROUGE.getCaseEchelleColor());
				else if ((j>=9 && i<=7) || (j==15 && i==8)) rec.setFill(Couleur.ROUGE.getCaseCheminColor());

				if (j>=10 && i>=10)  rec.setFill(Couleur.JAUNE.getCaseEcurieColor());
				else if (i==8 && (j<15 && j>8)) rec.setFill(Couleur.JAUNE.getCaseEchelleColor());
				else if (j>=9 && i>=9 || (j==8 && i==15)) rec.setFill(Couleur.JAUNE.getCaseCheminColor());

				if (j<=6 && i>=10)  rec.setFill(Couleur.VERT.getCaseEcurieColor());
				else if (j==8 && (i<15 && i>8)) rec.setFill(Couleur.VERT.getCaseEchelleColor());
				else if (j<=7 && i>=9  || (j==1 && i==8)) rec.setFill(Couleur.VERT.getCaseCheminColor());

				if(j==8 && i==8)  rec.setFill(Color.BLACK);

				Text t = new Text();
				GridPane.setRowIndex(t, i);
				GridPane.setHalignment(t, HPos.CENTER);
				GridPane.setValignment(t, VPos.CENTER);
				GridPane.setColumnIndex(t, j);
				GridPane.setRowIndex(rec, i);
				GridPane.setColumnIndex(rec, j);
				grilleContenu.getChildren().addAll(rec, t);
				
				affichageManager.addText(id, t);
			}

		stage.setScene(scene);
		stage.setTitle("Jeu des petits chevaux");
		stage.setResizable(false);
		stage.show();
		
		affichageManager.openMenuChargementSauvegarde();
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
	
	public static Main getInstance() {
		return instance;
	}

	public SaveManager getSaveManager() {
		return saveManager;
	}
}



