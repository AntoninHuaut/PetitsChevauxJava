package fr.huautleroux.petitschevaux.enums;

public enum JoueurAction {
	
	RIEN_FAIRE("Ne rien faire"),
	SORTIR_CHEVAL("Sortir un cheval", "sortir de l'écurie"),
	DEPLACER_CHEVAL("Déplacer un cheval", "déplacer sur le plateau"),
	SAUVEGARDER("Sauvegarder");
	
	private String nom;
	private String message = "";
	
	private JoueurAction(String nom) {
		this.nom = nom;
	}
	
	private JoueurAction(String nom, String message) {
		this(nom);
		this.message = message;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getMessage() {
		return message;
	}
}
