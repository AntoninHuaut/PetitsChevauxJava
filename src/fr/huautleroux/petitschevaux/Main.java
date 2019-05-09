package fr.huautleroux.petitschevaux;

import fr.huautleroux.petitschevaux.affichage.console.CCouleurs;
import fr.huautleroux.petitschevaux.affichage.console.IConsole;
import fr.huautleroux.petitschevaux.affichage.console.Saisie;
import fr.huautleroux.petitschevaux.affichage.graphique.ApplicationFX;

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
		if (peutUtiliserJavaFX()) {
			if (!UTILISER_INTERFACE) {
				System.out.print("Souhaitez-vous lancer le jeu avec l'interface graphique ? O(ui) / N(on) : ");
				UTILISER_INTERFACE = Saisie.asBoolean();
				System.out.println("");
			}
		} else if (UTILISER_INTERFACE) {
			System.out.println(CCouleurs.RED_BRIGHT + "\nImpossible de lancer l'interface graphique. JavaFX n'est pas installé !\n" + CCouleurs.RESET);
			UTILISER_INTERFACE = false;
		}

		if (UTILISER_INTERFACE)
			ApplicationFX.startApplication();
		else
			new IConsole().start();
	}

	/**
	 * @return L'utilisateur souhaite utiliser le dé truqué
	 */
	public static boolean utiliser_DeTruque() {
		return UTILISER_DETRUQUE;
	}

	/**
	 * @return L'utilisateur souhaite utiliser l'interface
	 */
	public static boolean utilise_Interface() {
		return UTILISER_INTERFACE;
	}

	/**
	 * @return L'utilisateur peut utiliser l'interface graphique
	 */
	public boolean peutUtiliserJavaFX() {
		try {
			Class.forName("javafx.application.Application");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}



