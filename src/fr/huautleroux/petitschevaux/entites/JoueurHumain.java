package fr.huautleroux.petitschevaux.entites;

import java.util.List;

import fr.huautleroux.petitschevaux.Main;
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
		
		return Main.getInstance().getPopup().getJoueurAction(de, actionsDispo, actionDefaut, this);
	};

	@Override
	public Pion choisirPion(int de, JoueurAction action, Plateau plateau) {
		List<Pion> pionsDispo = getPionsParAction(action);
		return Main.getInstance().getPopup().getJoueurPion(action, pionsDispo, this);
	}
}
