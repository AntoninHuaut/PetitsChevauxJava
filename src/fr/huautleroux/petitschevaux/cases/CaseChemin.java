package fr.huautleroux.petitschevaux.cases;

import java.util.List;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.entites.Pion;

public class CaseChemin extends Case {
	
	public CaseChemin(int numero) {
		super(numero);
	}
	
	@Override
	public boolean peutPasser(Pion pionPasser) {
		List<Pion> pionsEnemy = getChevaux().stream().filter(pion -> !pion.getCouleur().equals(pionPasser.getCouleur())).collect(Collectors.toList());
		return pionsEnemy.isEmpty();
	}

	@Override
	public boolean peutSArreter(Pion pion, int de) {
		return true;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " " + toString();
	}
	
	/**
	 * Vérifie si la case permet l'accès à l'échelle pour un joueur
	 * @param idJoueur Numéro du joueur
	 * @return Vrai si le case permet l'accès à l'échelle
	 */
	public boolean isAccesEchelle(int idJoueur) {
		return getNumero() % 14 == 0 && idJoueur * 14 == getNumero();
	}
}
