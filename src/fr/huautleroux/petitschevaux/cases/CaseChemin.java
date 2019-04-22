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
		return getClass().getName() + toStringPions();
	}
	
	public boolean isAccesEchelle() {
		return getNumero() % 14 == 0;
	}
}
