package fr.huautleroux.petitschevaux;

public abstract class Participant {
	private String pseudo;
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String value) {
		this.pseudo = value;
	}
	
	public int tirageDee() {
		return 0;
	}
	
	public abstract void choixAction(Actions action); 
	
	
}
