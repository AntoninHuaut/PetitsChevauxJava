
package fr.huautleroux.petitschevaux.enums;

import fr.huautleroux.petitschevaux.affichage.console.CCouleurs;
import fr.huautleroux.petitschevaux.affichage.graphique.GCouleurs;

public enum Couleur {
	
	JAUNE(GCouleurs.JAUNE_ECURIE, GCouleurs.JAUNE_ECHELLE, GCouleurs.JAUNE_CHEMIN, GCouleurs.JAUNE_TEXT, CCouleurs.YELLOW_BRIGHT, CCouleurs.YELLOW),
	BLEU(GCouleurs.BLEU_ECURIE, GCouleurs.BLEU_ECHELLE, GCouleurs.BLEU_CHEMIN, CCouleurs.CYAN_BRIGHT, CCouleurs.CYAN),
	VERT(GCouleurs.VERT_ECURIE, GCouleurs.VERT_ECHELLE, GCouleurs.VERT_CHEMIN, CCouleurs.GREEN_BRIGHT, CCouleurs.GREEN),
	ROUGE(GCouleurs.ROUGE_ECURIE, GCouleurs.ROUGE_ECHELLE, GCouleurs.ROUGE_CHEMIN, CCouleurs.RED_BRIGHT, CCouleurs.RED);
	
	public static final String SYMBOL = "🐎";
	
	/* Interface graphique */
	private GCouleurs ecurieCouleurIG;
	private GCouleurs echelleCouleurIG;
	private GCouleurs cheminCouleurIG;
	private GCouleurs textCouleurIG;
	
	/* Console */
	private String textCouleurIC;
	private String textCouleurFonceIC;
	
	private Couleur(GCouleurs ecurieCouleurIG, GCouleurs echelleCouleurIG, GCouleurs cheminCouleurIG, GCouleurs textCouleurIG, String textCouleurIC, String textCouleurFonceIC) {
		this.ecurieCouleurIG = ecurieCouleurIG;
		this.echelleCouleurIG = echelleCouleurIG;
		this.cheminCouleurIG = cheminCouleurIG;
		this.textCouleurIG = textCouleurIG;
		this.textCouleurIC = textCouleurIC;
		this.textCouleurFonceIC = textCouleurFonceIC;
	}
	
	private Couleur(GCouleurs caseEcurieCouleur, GCouleurs caseEchelleCouleur, GCouleurs caseCheminCouleur, String textCouleurIC, String textCouleurFonceIC) {
		this(caseEcurieCouleur, caseEchelleCouleur, caseCheminCouleur, caseEcurieCouleur, textCouleurIC, textCouleurFonceIC);
	}
	
	public String getTextCouleurIC() {
		return textCouleurIC;
	}
	
	public String getTextCouleurFonceIC() {
		return textCouleurFonceIC;
	}
	
	public String getTextCouleurIG() {
		return textCouleurIG.getCouleur();
	}
	
	public String getEcurieCouleurIG() {
		return ecurieCouleurIG.getCouleur();
	}

	public String getEchelleCouleurIG() {
		return echelleCouleurIG.getCouleur();
	}

	public String getCheminCouleurIG() {
		return cheminCouleurIG.getCouleur();
	}
	
	@Override
	public String toString() {
		return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

}
