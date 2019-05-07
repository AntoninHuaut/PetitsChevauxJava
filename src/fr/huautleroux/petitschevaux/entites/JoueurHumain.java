package fr.huautleroux.petitschevaux.entites;

import java.util.List;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.affichage.console.Saisie;
import fr.huautleroux.petitschevaux.affichage.graphique.IGraphique;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	@Override
	public JoueurAction choixAction(int de, Plateau plateau) {
		List<JoueurAction> actionsDispo = getActionsDisponible(de, plateau);
		
		JoueurAction actionDefaut = JoueurAction.RIEN_FAIRE;
		
		if (actionsDispo.contains(JoueurAction.SORTIR_CHEVAL))
			actionDefaut = JoueurAction.SORTIR_CHEVAL;
		else if (actionsDispo.contains(JoueurAction.DEPLACER_CHEVAL))
			actionDefaut = JoueurAction.DEPLACER_CHEVAL;
		
		if (Main.utilise_Interface())
			return IGraphique.getInstance().getPopup().getJoueurAction(de, actionsDispo, actionDefaut, this);
		else {
			actionsDispo.forEach(action -> System.out.println("  [" + action.ordinal() + "] " + action));
			System.out.println("");
			int numeroAction;
			
			do {
				System.out.print("  Entrez le numéro de l'action que vous souhaitez effectuer : ");
				numeroAction = Saisie.asInt();
			} while (!actionValide(numeroAction, actionsDispo));
			
			System.out.println("");
			return JoueurAction.values()[numeroAction];
		}
	};

	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) {
		List<Pion> pionsDispo = getPionsParAction(action);
		
		if (Main.utilise_Interface())
			return IGraphique.getInstance().getPopup().getJoueurPion(action, pionsDispo, this);
		else {
			pionsDispo.forEach(pion -> System.out.println("  " + pion));
			System.out.println("");
			int numeroPion;
			
			do {
				System.out.print("  Entrez le numéro du pion : ");
				numeroPion = Saisie.asInt() - 1;
			} while (!pionValide(numeroPion, pionsDispo));
			
			System.out.println("");
			return getChevaux().get(numeroPion);
		}
	}
	
	private boolean pionValide(int numeroPion, List<Pion> pionsDispo) {
		for (Pion pion : pionsDispo)
			if (pion.getId() == numeroPion)
				return true;
		
		return false;
	}
	
	private boolean actionValide(int numeroAction, List<JoueurAction> actionsDispo) {
		for (JoueurAction action : actionsDispo)
			if (action.ordinal() == numeroAction)
				return true;
		
		return false;
	}
}
