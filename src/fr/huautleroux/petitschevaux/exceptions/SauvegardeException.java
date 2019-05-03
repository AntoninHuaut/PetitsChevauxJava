package fr.huautleroux.petitschevaux.exceptions;

/**
 * Erreur générée lorsque qu'une sauvegarde a échoué
 */
@SuppressWarnings("serial")
public class SauvegardeException extends Exception {
	
	private final static String MSG = "Impossible de sauvegarder : ";

	public SauvegardeException() {
		super(MSG);
	}

	public SauvegardeException(String s) {
		super(MSG + s);
	}

	public SauvegardeException(Throwable thr) {
		super(thr);
	}

	public SauvegardeException(String s, Throwable thr){
		super(MSG + s, thr);	
	}

	public SauvegardeException(String s, Throwable thr, boolean b1, boolean b2) {
		super(MSG + s, thr, b1, b2);
	}
}
