package fr.huautleroux.petitschevaux.entites;

import java.util.ArrayList;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	@Override
	public Pion choisirPion(int de, Plateau plateau) {
		System.out.println("Vous avez fait un " + de);

		if(hasToutPionEcurie(plateau)) { /* Tout les chevaux sont dans l'écurie */
			if(de == 6) 
				System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");
			else
				System.out.println("  Vous ne pouvez rien faire car tout vos chevaux sont dans l'écurie");
		}

		else { /* Il y a des pions sur le plateau */
			if(de == 6 && hasPionEcurie(plateau))
				System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");

			System.out.println("  Vous pouvez déplacer un cheval sur le plateau [2]");
		}

		return null;
	}

	private boolean hasToutPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) == 4;
	}

	private boolean hasPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) != 0;
	}

	private int getNombrePionEcurie(Plateau plateau) {
		return getPionEcurie(plateau).size();
	}
	
	private ArrayList<Pion> getPionEcurie(Plateau plateau) {
		ArrayList<Pion> pions = new ArrayList<Pion>();

		for(Pion pion : getChevaux()) {
			ArrayList<Case> cases = new ArrayList<Case>();
			plateau.getEcuries().forEach(c -> cases.add(c));
			plateau.getChemin().forEach(c -> cases.add(c));
			plateau.getEchelles().forEach(get -> get.forEach(c -> cases.add(c)));
			
			boolean containsPion = false;
			
			for(int i = 0; i < cases.size() && !containsPion; i++)
				if(cases.get(i).getChevaux().contains(pion))
					containsPion = true;
			
			if(!containsPion)
				pions.add(pion);
		}

		return pions;
	}

}
