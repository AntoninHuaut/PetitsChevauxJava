
package fr.huautleroux.petitschevaux.enums;

import javafx.scene.paint.Color;

public enum Couleur {
	
	JAUNE(Color.GOLD, Color.web("#ffff33"), Color.LIGHTGOLDENRODYELLOW, Color.ORANGE),
	BLEU(Color.web("#0073e6"), Color.web("#3399ff"), Color.web("#99ccff")),
	VERT(Color.GREEN, Color.web("#00e600"), Color.LIGHTGREEN),
	ROUGE(Color.RED, Color.web("#ff6666"), Color.PINK);
	
	public static String symbol = "🐎";
	private Color textCouleur;
	private Color caseEcurieCouleur;
	private Color caseEchelleCouleur;
	private Color caseCheminCouleur;
	
	private Couleur(Color caseEcurieCouleur, Color caseEchelleCouleur, Color caseCheminCouleur, Color textCouleur) {
		this.caseEcurieCouleur = caseEcurieCouleur;
		this.caseEchelleCouleur = caseEchelleCouleur;
		this.caseCheminCouleur = caseCheminCouleur;
		this.textCouleur = textCouleur;
	}
	
	private Couleur(Color caseEcurieCouleur, Color caseEchelleCouleur, Color caseCheminCouleur) {
		this(caseEcurieCouleur, caseEchelleCouleur, caseCheminCouleur, caseEcurieCouleur);
	}

	public Color getTextCouleur() {
		return textCouleur;
	}
	
	public Color getCaseEcurieCouleur() {
		return caseEcurieCouleur;
	}

	public Color getCaseEchelleCouleur() {
		return caseEchelleCouleur;
	}

	public Color getCaseCheminCouleur() {
		return caseCheminCouleur;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
