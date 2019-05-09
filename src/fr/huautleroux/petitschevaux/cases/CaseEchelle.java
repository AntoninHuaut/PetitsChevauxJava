package fr.huautleroux.petitschevaux.cases;

import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class CaseEchelle extends CaseColoree {

	public CaseEchelle(Couleur couleur, int numero) {
		super(couleur, numero);
	}

	/**
	 * Retourne le numéro de l'échelle du joueur
	 * @return Nombre entier en 0 et 5
	 */
	public int getNumeroLocal() {
		return getNumero() % 6;
	}

	@Override
	public boolean peutPasser(Pion pion) {
		return getChevaux().isEmpty();
	}

	@Override
	public boolean peutSArreter(Pion pion, int de) {
		return getChevaux().isEmpty() && getNumeroLocal () + 1 == de;
	}

	@Override
	public String toString() {
		return getClass().getName() + " " + toString();
	}
	
	/**
	 * Symbole de la case
	 * @return Symbole contenant le numéro de la case
	 */
	public String getSymbol() {
		switch(getNumeroLocal()) {
		case 0:
			return "❶";
		case 1:
			return "❷";
		case 2:
			return "❸";
		case 3:
			return "❹";
		case 4:
			return "❺";
		case 5:
			return "❻";
		default:
			return "⓿";
		}
	}
}
