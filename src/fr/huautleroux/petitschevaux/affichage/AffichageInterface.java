package fr.huautleroux.petitschevaux.affichage;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;

public interface AffichageInterface {

	public void debutTour(int numeroTour);
	public void tirageAuSort(Couleur couleur, String nomJoueur, Runnable callback);
	public void attendreToucheEntrer(Runnable callback);
	public void finDePartie(int numeroTour, Joueur joueurGagnant);
	public void effacerAffichage();
	public void miseAJourAffichage(Plateau plateau);
	public void simpleMessage(String msg, String color);

	/**
	 * Retourne la couleur par son nom
	 * @param couleurStr Nom de la couleur
	 * @return Couleur
	 */
	public default Couleur getCouleurString(String couleurStr) {
		if (couleurStr.isEmpty())
			return null;

		for (Couleur couleur : Couleur.values())
			if ((couleur.name().toLowerCase()).startsWith(couleurStr.toLowerCase()))
				return couleur;

		return null;
	}
}
