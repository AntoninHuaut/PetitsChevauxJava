
package fr.huautleroux.petitschevaux.enums;

import fr.huautleroux.petitschevaux.affichage.console.CCouleurs;

public enum Couleur {
	
	JAUNE("#FFD700", "#ffff33", "#FAFAD2", "#FFA500", CCouleurs.YELLOW_BRIGHT, CCouleurs.YELLOW),
	BLEU("#0073e6", "#3399ff", "#99ccff", CCouleurs.CYAN_BRIGHT, CCouleurs.CYAN),
	VERT("#008000", "#00e600", "#90EE90", CCouleurs.GREEN_BRIGHT, CCouleurs.GREEN),
	ROUGE("#FF0000", "#ff6666", "#FFC0CB", CCouleurs.RED_BRIGHT, CCouleurs.RED);
	
	public static final String SYMBOL = "🐎";
	
	/* Interface graphique */
	private String ecurieCouleurIG;
	private String echelleCouleurIG;
	private String cheminCouleurIG;
	private String textCouleurIG;
	
	/* Console */
	private String textCouleurIC;
	private String textCouleurFonceIC;
	
	private Couleur(String ecurieCouleurIG, String echelleCouleurIG, String cheminCouleurIG, String textCouleurIG, String textCouleurIC, String textCouleurFonceIC) {
		this.ecurieCouleurIG = ecurieCouleurIG;
		this.echelleCouleurIG = echelleCouleurIG;
		this.cheminCouleurIG = cheminCouleurIG;
		this.textCouleurIG = textCouleurIG;
		this.textCouleurIC = textCouleurIC;
		this.textCouleurFonceIC = textCouleurFonceIC;
	}
	
	private Couleur(String caseEcurieCouleur, String caseEchelleCouleur, String caseCheminCouleur, String textCouleurIC, String textCouleurFonceIC) {
		this(caseEcurieCouleur, caseEchelleCouleur, caseCheminCouleur, caseEcurieCouleur, textCouleurIC, textCouleurFonceIC);
	}

	public String getTextCouleurIG() {
		return textCouleurIG;
	}
	
	public String getTextCouleurIC() {
		return textCouleurIC;
	}
	
	public String getTextCouleurFonceIC() {
		return textCouleurFonceIC;
	}
	
	public String getEcurieCouleurIG() {
		return ecurieCouleurIG;
	}

	public String getEchelleCouleurIG() {
		return echelleCouleurIG;
	}

	public String getCheminCouleurIG() {
		return cheminCouleurIG;
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
