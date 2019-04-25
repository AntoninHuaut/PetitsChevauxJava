package fr.huautleroux.petitschevaux.utils;

import java.util.Scanner;

import fr.huautleroux.petitschevaux.enums.Couleur;

/**
 * Gestion de la saisie utilisateur
 */
public class Saisie {

	private static Scanner scanner = new Scanner(System.in);
	
	/**
	 * @return Chaîne de caractère
	 */
	public static String asString() {
		return scanner.nextLine();
	}
	
	/**
	 * @return Chaîne de caractère qui n'est pas vide
	 */
	public static String asStringNoEmpty() {
		String get = asString();
		return get.isEmpty() ? asStringNoEmpty() : get;
	}
	
	/**
	 * @return Couleur obtenue par son nom ou sa première lettre
	 */
	public static Couleur asCouleur() {
		try {
			String couleurStr = asStringNoEmpty();
			
			if (couleurStr.length() == 1)
				for (Couleur couleur : Couleur.values())
					if (("" + couleur.getSymbol()).equals(couleurStr.toLowerCase()))
						return couleur;
			
			return Couleur.valueOf(couleurStr.toUpperCase());
		} catch(IllegalArgumentException e) {
			errorMsg("une couleur", "  ");
			return asCouleur();
		}
	}

	/**
	 * @return Nombre entier
	 */
	public static int asInt() {
		try {
			return Integer.valueOf(asStringNoEmpty());
		} catch(NumberFormatException e) {
			errorMsg("un nombre entier");
			return asInt();
		}
	}
	/**
	 * @return Nombre décimal (double précision)
	 */
	public static double asDouble() {
		try {
			return Double.valueOf(asStringNoEmpty().replace(',', '.'));
		} catch(NumberFormatException e) {
			errorMsg("un nombre décimal (double précision)");
			return asDouble();
		}
	}
	/**
	 * @return Nombre décimal (simple précision)
	 */
	public static float asFloat() {
		try {
			return (float) asDouble();
		} catch(NumberFormatException e) {
			errorMsg("un nombre décimal (simple précision)");
			return asFloat();
		}
	}
	/**
	 * @return Boolean obtenue par un o(ui) / n(on)
	 */
	public static boolean asBoolean() {
		String get = asStringNoEmpty().toLowerCase();
		if(get.equals("o") || get.equals("oui"))
			return true;
		else if(get.equals("n") || get.equals("non"))
			return false;
		
		errorMsg("O(ui) / N(on)");
		return asBoolean();
	}
	
	private static void errorMsg(String msg, String prefix) {
		System.out.print(prefix + Utils.RED_BRIGHT + "Entrée invalide" + Utils.RESET + ", veuillez rentrer " + msg + " : ");
	}

	private static void errorMsg(String msg) {
		errorMsg(msg, "");
	}
}
