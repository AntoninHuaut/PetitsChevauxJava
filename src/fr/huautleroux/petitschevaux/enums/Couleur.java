package fr.huautleroux.petitschevaux.enums;

import fr.huautleroux.petitschevaux.utils.Utils;
import javafx.scene.paint.Color;

public enum Couleur {
	
	JAUNE('j', Utils.YELLOW_BRIGHT, Color.GOLD, Color.YELLOW, Color.LIGHTGOLDENRODYELLOW),
	BLEU('b', Utils.CYAN_BRIGHT, Color.BLUE, Color.DODGERBLUE, Color.LIGHTBLUE),
	VERT('v', Utils.GREEN_BRIGHT, Color.GREEN, Color.GREENYELLOW, Color.LIGHTGREEN),
	ROUGE('r', Utils.RED_BRIGHT, Color.RED, Color.ORANGERED, Color.PINK);
	
	private char symbol;
	private String textColor;
	private Color caseEcurieColor;
	private Color caseEchelleColor;
	private Color caseCheminColor;
	
	private Couleur(char c, String textColor, Color caseEcurieColor, Color caseEchelleColor, Color caseCheminColor) {
		this.symbol = c;
		this.textColor = textColor;
		this.caseEcurieColor = caseEcurieColor;
		this.caseEchelleColor = caseEchelleColor;
		this.caseCheminColor = caseCheminColor;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public String getTextColor() {
		return textColor;
	}
	
	public Color getCaseEcurieColor() {
		return caseEcurieColor;
	}

	public Color getCaseEchelleColor() {
		return caseEchelleColor;
	}

	public Color getCaseCheminColor() {
		return caseCheminColor;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
