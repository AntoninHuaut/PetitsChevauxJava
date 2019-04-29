
package fr.huautleroux.petitschevaux.enums;

import javafx.scene.paint.Color;

public enum Couleur {
	
	JAUNE('◼', Color.GOLD, Color.YELLOW, Color.LIGHTGOLDENRODYELLOW),
	BLEU('▲', Color.DODGERBLUE, Color.DEEPSKYBLUE, Color.LIGHTBLUE),
	VERT('◆', Color.GREEN, Color.GREENYELLOW, Color.LIGHTGREEN),
	ROUGE('⚫', Color.RED, Color.ORANGERED, Color.PINK);
	
	private char symbol;
	private Color principalColor;
	private Color caseEcurieColor;
	private Color caseEchelleColor;
	private Color caseCheminColor;
	
	private Couleur(char c, Color caseEcurieColor, Color caseEchelleColor, Color caseCheminColor) {
		this.symbol = c;
		this.principalColor = caseEcurieColor;
		this.caseEcurieColor = caseEcurieColor;
		this.caseEchelleColor = caseEchelleColor;
		this.caseCheminColor = caseCheminColor;

	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public Color getPrincipalColor() {
		return principalColor;
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
