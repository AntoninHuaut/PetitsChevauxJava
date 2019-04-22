package fr.huautleroux.petitschevaux.entites;

import java.util.List;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	@Override
	public JoueurAction choixAction(int de, Plateau plateau) {
		System.out.println("Vous avez fait " + de + " au dé");
		List<JoueurAction> actionDispo = getActionDisponible(de, plateau, true);

		System.out.print("\nVeuillez entrer le numéro de l'action que vous souhaitez effectuer : ");

		int choix;

		do {
			choix = Saisie.asInt();
			System.out.println("");
		} while(!estChoixValide(choix, actionDispo));

		return JoueurAction.values()[choix];
	};

	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) {
		List<Pion> pionsAction = getPionsParAction(action);

		System.out.println("Voici la liste de vos chevaux que vous pouvez " + action.getMessage());

		pionsAction.forEach(pion -> System.out.println("  • " + pion));
		System.out.print("\nVeuillez entrer le numéro du cheval que vous souhaitez " + action.getMessage() + " : ");

		boolean pionAutorise = false;
		int numPion;

		do {
			numPion = Saisie.asInt() - 1;
			System.out.println("");

			for (Pion pion : pionsAction)
				if(pion.getId() == numPion)
					pionAutorise = true;

		} while (!pionAutorise);

		return pionsAction.get(numPion);
	}
}
