package fr.huautleroux.petitschevaux.affichage.console;

import java.util.Scanner;

import fr.huautleroux.petitschevaux.enums.Couleur;

public class Saisie {

	private static Scanner scanner = new Scanner(System.in);

	public static String asString() {
		return scanner.nextLine();
	}

	public static String asStringNoEmpty() {
		String get = asString();
		return get.isEmpty() ? asStringNoEmpty() : get;
	}

	public static Couleur asCouleur() {
		try {
			Couleur couleur = Utils.getCouleurString(asStringNoEmpty());

			if (couleur == null)
				throw new IllegalArgumentException();

			return couleur;
		} catch(IllegalArgumentException e) {
			errorMsg("une couleur");
			return asCouleur();
		}
	}

	public static int asInt() {
		try {
			return Integer.valueOf(asStringNoEmpty());
		} catch(NumberFormatException e) {
			errorMsg("un nombre entier");
			return asInt();
		}
	}

	public static double asDouble() {
		try {
			return Double.valueOf(asStringNoEmpty().replace(',', '.'));
		} catch(NumberFormatException e) {
			errorMsg("un nombre décimal (double précision)");
			return asDouble();
		}
	}

	public static float asFloat() {
		try {
			return (float) asDouble();
		} catch(NumberFormatException e) {
			errorMsg("un nombre décimal (simple précision)");
			return asFloat();
		}
	}

	public static boolean asBoolean() {
		String get = asStringNoEmpty().toLowerCase();
		if(get.equals("o") || get.equals("oui"))
			return true;
		else if(get.equals("n") || get.equals("non"))
			return false;

		errorMsg("O(ui) / N(on)");
		return asBoolean();
	}

	private static void errorMsg(String msg) {
		System.out.println("Entrée invalide, veuillez rentrer " + msg);
	}
}