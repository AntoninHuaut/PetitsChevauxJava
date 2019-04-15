package fr.huautleroux.petitschevaux.exceptions;

@SuppressWarnings("serial")
public class CasePleineException extends Exception {

	public CasePleineException() {
		super("La case est pleine");
	}

	public CasePleineException(String s) {
		super(s);
	}

	public CasePleineException(Throwable thr) {
		super(thr);
	}

	public CasePleineException(String s, Throwable thr){
		super(s, thr);
	}

	public CasePleineException(String s, Throwable thr, boolean b1, boolean b2) {
		super(s, thr, b1, b2);
	}
}
