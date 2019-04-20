package fr.huautleroux.petitschevaux.enums;

public enum Couleur {
	
	JAUNE('j'),
	BLEU('b'),
	VERT('v'),
	ROUGE('r');
	
	private char symbol;
	
	private Couleur(char c) {
		this.symbol = c;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
