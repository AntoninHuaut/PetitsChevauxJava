
package fr.huautleroux.petitschevaux.enums;

import fr.huautleroux.petitschevaux.affichage.console.Utils;
import javafx.scene.paint.Color;

public enum Couleur {
	
	JAUNE(Color.GOLD, Color.web("#ffff33"), Color.LIGHTGOLDENRODYELLOW, Color.ORANGE, Utils.YELLOW_BRIGHT),
	BLEU(Color.web("#0073e6"), Color.web("#3399ff"), Color.web("#99ccff"), Utils.BLUE_BRIGHT),
	VERT(Color.GREEN, Color.web("#00e600"), Color.LIGHTGREEN, Utils.GREEN_BRIGHT),
	ROUGE(Color.RED, Color.web("#ff6666"), Color.PINK, Utils.RED_BRIGHT);
	
	public static final String SYMBOL = "🐎";
	
	private Color ecurieCouleurIG;
	private Color echelleCouleurIG;
	private Color cheminCouleurIG;
	private Color textCouleurIG;
	private String textCouleurIC;
	
	private Couleur(Color ecurieCouleurIG, Color echelleCouleurIG, Color cheminCouleurIG, Color textCouleurIG, String textCouleurIC) {
		this.ecurieCouleurIG = ecurieCouleurIG;
		this.echelleCouleurIG = echelleCouleurIG;
		this.cheminCouleurIG = cheminCouleurIG;
		this.textCouleurIG = textCouleurIG;
		this.textCouleurIC = textCouleurIC;
	}
	
	private Couleur(Color caseEcurieCouleur, Color caseEchelleCouleur, Color caseCheminCouleur, String textCouleurIC) {
		this(caseEcurieCouleur, caseEchelleCouleur, caseCheminCouleur, caseEcurieCouleur, textCouleurIC);
	}

	public Color getTextCouleurIG() {
		return textCouleurIG;
	}
	
	public String getTextCouleurIC() {
		return textCouleurIC;
	}
	
	public Color getEcurieCouleurIG() {
		return ecurieCouleurIG;
	}

	public Color getEchelleCouleurIG() {
		return echelleCouleurIG;
	}

	public Color getCheminCouleurIG() {
		return cheminCouleurIG;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
