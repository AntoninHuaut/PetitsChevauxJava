package fr.huautleroux.petitschevaux.enums;

import fr.huautleroux.petitschevaux.utils.Utils;

public enum Couleur {
	
	JAUNE('j', Utils.YELLOW_BRIGHT, Utils.YELLOW_BACKGROUND),
	BLEU('b', Utils.CYAN_BRIGHT, Utils.CYAN_BACKGROUND),
	VERT('v', Utils.GREEN_BRIGHT, Utils.GREEN_BACKGROUND),
	ROUGE('r', Utils.RED_BRIGHT, Utils.RED_BACKGROUND);
	
	private char symbol;
	private String textColor;
	private String backgroundColor;
	
	private Couleur(char c, String textColor, String backgroundColor) {
		this.symbol = c;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public String getTextColor() {
		return textColor;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
