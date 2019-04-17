package fr.huautleroux.petitschevaux.utils;

import java.util.Scanner;

public class Saisie {
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static String asString() {
		return scanner.nextLine();
	}

	public static int asInt() {
		try {
			return Integer.valueOf(asString());
		} catch(NumberFormatException e) {
			errorMsg("entier");
			return asInt();
		}
	}

	public static double asDouble() {
		try {
			return Double.valueOf(asString().replace(',', '.'));
		} catch(NumberFormatException e) {
			errorMsg("décimal (double précision)");
			return asDouble();
		}
	}

	public static float asFloat() {
		try {
			return (float) asDouble();
		} catch(NumberFormatException e) {
			errorMsg("décimal (simple précision)");
			return asFloat();
		}
	}

	private static void errorMsg(String msg) {
		System.out.println("Entrée invalide, veuillez rentrer un nombre " + msg);
	}
}
