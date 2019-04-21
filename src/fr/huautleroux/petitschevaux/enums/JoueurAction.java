package fr.huautleroux.petitschevaux.enums;

public enum JoueurAction {
	
	RIEN_FAIRE(),
	SORTIR_CHEVAL("sortir de l'écurie"),
	DEPLACER_CHEVAL("déplacer sur le plateau"),
	SAUVEGARDER();
	
	private String message = "";
	
	private JoueurAction() {}
	
	private JoueurAction(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
