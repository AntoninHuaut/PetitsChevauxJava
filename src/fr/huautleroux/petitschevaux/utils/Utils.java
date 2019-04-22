package fr.huautleroux.petitschevaux.utils;

import java.io.IOException;

public class Utils {

	public static void effacerAffichage() {
		String os = System.getProperty("os.name");

		try {
			if (os.contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				System.out.print("\033[H\033[2J");
		} catch(IOException | InterruptedException e) {}
	}
}
