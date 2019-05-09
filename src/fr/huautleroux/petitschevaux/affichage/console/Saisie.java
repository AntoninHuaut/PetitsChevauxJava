package fr.huautleroux.petitschevaux.affichage.console;

import java.util.Scanner;

import fr.huautleroux.petitschevaux.affichage.AffichageInterface;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class Saisie {

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Saisie utilisateur d'une chaîne de caractère
	 * @return Chaîne de caractère
	 */
	public static String asString() {
		return scanner.nextLine();
	}

	/**
	 * Saisie utilisateur d'une chaîne de caractère non vide
	 * @return Chaîne de caractère non vide
	 */
	public static String asStringNoEmpty() {
		String get = asString();
		return get.isEmpty() ? asStringNoEmpty() : get;
	}

	/**
	 * Saisie utilisateur d'une couleur
	 * @param affichage Interface utilisée pour l'affichage
	 * @return Couleur du jeu
	 */
	public static Couleur asCouleur(AffichageInterface affichage) {
		try {
			Couleur couleur = affichage.getCouleurString(asStringNoEmpty());

			if (couleur == null)
				throw new IllegalArgumentException();

			return couleur;
		} catch(IllegalArgumentException e) {
			errorMsg("une couleur");
			return asCouleur(affichage);
		}
	}

	/**
	 * Saisie utilisateur d'un nombre entier
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
	 * Saisie utilisateur d'un O(ui) / N(on)
	 * @return Boolean
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

	private static void errorMsg(String msg) {
		System.out.println("Entrée invalide, veuillez rentrer " + msg);
	}
}