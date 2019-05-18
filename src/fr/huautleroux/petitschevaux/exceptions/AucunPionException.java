package fr.huautleroux.petitschevaux.exceptions;

/**
 * Erreur générée lorsqu'aucun pion n'est disponible
 */
@SuppressWarnings("serial")
public class AucunPionException extends Exception {
	
	private final static String MSG = "Impossible de trouver un pion : ";

	public AucunPionException() {
		super(MSG);
	}

	public AucunPionException(String s) {
		super(MSG + s);
	}

	public AucunPionException(Throwable thr) {
		super(thr);
	}

	public AucunPionException(String s, Throwable thr){
		super(MSG + s, thr);	
	}

	public AucunPionException(String s, Throwable thr, boolean b1, boolean b2) {
		super(MSG + s, thr, b1, b2);
	}
}
