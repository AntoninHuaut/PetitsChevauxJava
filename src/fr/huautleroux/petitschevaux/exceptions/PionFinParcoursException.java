package fr.huautleroux.petitschevaux.exceptions;

/**
 * Erreur générée lorsqu'un pion a terminé le parcours et a atteint la case maximum
 */
@SuppressWarnings("serial")
public class PionFinParcoursException extends Exception {
	
	private final static String MSG = "Le pion a terminé le parcours, il ne peut plus se déplacer";

	public PionFinParcoursException() {
		super(MSG);
	}

	public PionFinParcoursException(String s) {
		super(MSG + s);
	}

	public PionFinParcoursException(Throwable thr) {
		super(thr);
	}

	public PionFinParcoursException(String s, Throwable thr){
		super(MSG + s, thr);	
	}

	public PionFinParcoursException(String s, Throwable thr, boolean b1, boolean b2) {
		super(MSG + s, thr, b1, b2);
	}
}
