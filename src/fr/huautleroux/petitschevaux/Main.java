package fr.huautleroux.petitschevaux;

import fr.huautleroux.petitschevaux.affichage.console.IConsole;
import fr.huautleroux.petitschevaux.affichage.console.Saisie;
import fr.huautleroux.petitschevaux.affichage.graphique.ApplicationFX;
import javafx.application.Application;

public class Main {

	private static boolean UTILISER_DETRUQUE = false;
	private static boolean UTILISER_INTERFACE = false;

	public static void main(String[] args) {
		for (String arg : args) {
			if (arg.equals("-de"))
				UTILISER_DETRUQUE = true;

			if (arg.equals("-interface"))
				UTILISER_INTERFACE = true;
		}

		new Main();
	}

	public Main() {
		if (!UTILISER_INTERFACE) {
			System.out.print("Souhaitez-vous lancer le jeu avec l'interface graphique ? O(ui) / N(on) : ");
			UTILISER_INTERFACE = Saisie.asBoolean();
			System.out.println("");
		}

		if (UTILISER_INTERFACE)
			Application.launch(ApplicationFX.class, new String [] {});
		else
			new IConsole().start();
	}

	public static boolean utiliser_DeTruque() {
		return UTILISER_DETRUQUE;
	}

	public static boolean utilise_Interface() {
		return UTILISER_INTERFACE;
	}
}



