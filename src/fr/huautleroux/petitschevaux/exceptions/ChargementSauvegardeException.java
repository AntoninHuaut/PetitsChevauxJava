package fr.huautleroux.petitschevaux.exceptions;

@SuppressWarnings("serial")
public class ChargementSauvegardeException extends Exception {
	
	private final static String MSG = "Impossible de charger la sauvegarde : ";

	public ChargementSauvegardeException() {
		super(MSG);
	}

	public ChargementSauvegardeException(String s) {
		super(MSG + s);
	}

	public ChargementSauvegardeException(Throwable thr) {
		super(thr);
	}

	public ChargementSauvegardeException(String s, Throwable thr){
		super(MSG + s, thr);	
	}

	public ChargementSauvegardeException(String s, Throwable thr, boolean b1, boolean b2) {
		super(MSG + s, thr, b1, b2);
	}
}
