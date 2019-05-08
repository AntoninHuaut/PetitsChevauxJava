package fr.huautleroux.petitschevaux.affichage.graphique;

public enum GCouleurs {

	VIOLET("9370DB");
	
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
