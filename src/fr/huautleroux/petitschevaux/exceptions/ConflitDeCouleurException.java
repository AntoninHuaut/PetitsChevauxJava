package fr.huautleroux.petitschevaux.exceptions;

@SuppressWarnings("serial")
public class ConflitDeCouleurException extends Exception {
	
	public ConflitDeCouleurException() {
		super("Conflit de couleur");
	}
	
	public ConflitDeCouleurException(String s) {
		super(s);
	}
	
	public ConflitDeCouleurException(Throwable thr) {
		super(thr);
	}
	
	public ConflitDeCouleurException(String s, Throwable thr){
		super(s, thr);
	}
	
	public ConflitDeCouleurException(String s, Throwable thr, boolean b1, boolean b2) {
		super(s, thr, b1, b2);
	}
	
}
