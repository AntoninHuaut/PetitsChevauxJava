
package fr.huautleroux.petitschevaux.enums;

import fr.huautleroux.petitschevaux.affichage.console.CCouleurs;

public enum Couleur {
	
	JAUNE("#FFD700", "#ffff33", "#FAFAD2", "#FFA500", CCouleurs.YELLOW_BRIGHT),
	BLEU("#0073e6", "#3399ff", "#99ccff", CCouleurs.BLUE_BRIGHT),
	VERT("#008000", "#00e600", "#90EE90", CCouleurs.GREEN_BRIGHT),
	ROUGE("#FF0000", "#ff6666", "#FFC0CB", CCouleurs.RED_BRIGHT);
	
	public static final String SYMBOL = "🐎";
	
	/* Interface graphique */
	private String ecurieCouleurIG;
	private String echelleCouleurIG;
	private String cheminCouleurIG;
	private String textCouleurIG;
	
	/* Console */
	private String textCouleurIC;
	
	private Couleur(String ecurieCouleurIG, String echelleCouleurIG, String cheminCouleurIG, String textCouleurIG, String textCouleurIC) {
		this.ecurieCouleurIG = ecurieCouleurIG;
		this.echelleCouleurIG = echelleCouleurIG;
		this.cheminCouleurIG = cheminCouleurIG;
		this.textCouleurIG = textCouleurIG;
		this.textCouleurIC = textCouleurIC;
	}
	
	private Couleur(String caseEcurieCouleur, String caseEchelleCouleur, String caseCheminCouleur, String textCouleurIC) {
		this(caseEcurieCouleur, caseEchelleCouleur, caseCheminCouleur, caseEcurieCouleur, textCouleurIC);
	}

	public String getTextCouleurIG() {
		return textCouleurIG;
	}
	
	public String getTextCouleurIC() {
		return textCouleurIC;
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
