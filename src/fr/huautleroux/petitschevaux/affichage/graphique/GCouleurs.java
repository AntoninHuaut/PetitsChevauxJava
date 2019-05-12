package fr.huautleroux.petitschevaux.affichage.graphique;

public enum GCouleurs {

	VIOLET("9370DB"),
	JAUNE_ECURIE("FFD700"),
	JAUNE_ECHELLE("ffff33"),
	JAUNE_CHEMIN("FAFAD2"),
	JAUNE_TEXT("FFA500"),
	
	BLEU_ECURIE("0073e6"),
	BLEU_ECHELLE("3399ff"),
	BLEU_CHEMIN("99ccff"),
	
	VERT_ECURIE("008000"),
	VERT_ECHELLE("00e600"),
	VERT_CHEMIN("90EE90"),
	
	ROUGE_ECURIE("FF0000"),
	ROUGE_ECHELLE("ff6666"),
	ROUGE_CHEMIN("FFC0CB");
	
	private String couleur;
	
	private GCouleurs(String couleur) {
		this.couleur = couleur;
	}
	
	public String getCouleur() {
		return couleur;
	}
	
	@Override
	public String toString() {
		return couleur;
	}
}
