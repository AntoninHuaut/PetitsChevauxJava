package fr.huautleroux.petitschevaux.exceptions;

@SuppressWarnings("serial")
public class PasDeJoueursException extends Exception{

	public PasDeJoueursException() {
		super("Pas de joueur");
	}

	public PasDeJoueursException(String s) {
		super(s);
	}

	public PasDeJoueursException(Throwable thr) {
		super(thr);
	}

	public PasDeJoueursException(String s, Throwable thr){
		super(s, thr);	
	}

	public PasDeJoueursException(String s, Throwable thr, boolean b1, boolean b2) {
		super(s, thr, b1, b2);
	}
}
