
package fr.huautleroux.petitschevaux.enums;

import javafx.scene.paint.Color;

public enum Couleur {
	
	JAUNE(Color.ORANGE, Color.GOLD, Color.YELLOW, Color.LIGHTGOLDENRODYELLOW),
	BLEU(Color.DODGERBLUE, Color.DEEPSKYBLUE, Color.LIGHTBLUE),
	VERT(Color.GREEN, Color.GREENYELLOW, Color.LIGHTGREEN),
	ROUGE(Color.RED, Color.ORANGERED, Color.PINK);
	
	public static String symbol = "🐎";
	private Color textCouleur;
	private Color caseEcurieCouleur;
	private Color caseEchelleCouleur;
	private Color caseCheminCouleur;
	
	private Couleur(Color textCouleur, Color caseEcurieCouleur, Color caseEchelleCouleur, Color caseCheminCouleur) {
		this.textCouleur = textCouleur;
		this.caseEcurieCouleur = caseEcurieCouleur;
		this.caseEchelleCouleur = caseEchelleCouleur;
		this.caseCheminCouleur = caseCheminCouleur;

	}
	
	private Couleur(Color caseEcurieCouleur, Color caseEchelleCouleur, Color caseCheminCouleur) {
		this(caseEcurieCouleur, caseEcurieCouleur, caseEchelleCouleur, caseCheminCouleur);
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
