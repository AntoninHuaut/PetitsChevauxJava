package fr.huautleroux.petitschevaux.entites;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.utils.Saisie;

public class JoueurHumain extends Joueur {

	public JoueurHumain(String nom, Couleur couleur) {
		super(nom, couleur);
	}

	@Override
	public Pion choisirPion(int de, Plateau plateau) {
		Case caseCible;
		/*VÃ©rifier la position des Chevaux*/
		if (de == 6 /*&& chevaux.getPosition ? == caseDepart*/) {
			System.out.println("Vous avez fait un 6, vous pouvez sortir un cheval de l'écurie");
			int sais = Saisie.asInt();
			plateau.deplacerPionA(Joueur.chevaux.indexOf(sais), caseCible);
			return null; /*Pion avec nouvelles coord ?*/

		}
		else if (de == 6 /*&& chevaux.getPosition() == CaseChemin ou caseDepart ou caseDEchelle*/) {
			System.out.println("Vous avez fait un " +de + " , vous pouvez sortir un cheval de l'écurie ou en déplacer un sur le plateau");
			System.out.println("Chevaux disponibles : "  /*joueur.getChevaux()*/);
			System.out.println("Entrez le numéro du cheval à sortir");
			int sais = Saisie.asInt();
			plateau.deplacerPionA(Joueur.chevaux.indexOf(sais), caseCible);
			return null;/*Pion avec nouvelles coord ?*/
		}
		else if(de != 6 /*&& chevaux.getPosition() == CaseChemin ou caseDEchelle*/) {
			System.out.println("Vous avez fait " + de + " pouvez déplacer un cheval sur le plateau");
			System.out.println("Chevaux disponibles : "  /*joueur.getChevaux()*/);
			System.out.println("Entrez le numéro du cheval à sortir");
			int sais = Saisie.asInt();
			plateau.deplacerPionA(Joueur.chevaux.indexOf(sais), caseCible);
			return null;/*Pion avec nouvelles coord ?*/
			
		}
		else /*Pas de chevaux sur plateau*/ {
			System.out.println("Vous avez tiré un " + de + " Vous ne pouvez pas sortir de cheval");
		}
		return null;
	
	}

}
