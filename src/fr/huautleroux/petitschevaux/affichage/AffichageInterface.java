package fr.huautleroux.petitschevaux.affichage;

import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;

public interface AffichageInterface {

	/**
	 * Affiche le message de début du tour
	 * @param numeroTour Numéro du tour
	 */
	public void debutTour(int numeroTour);
	
	/**
	 * Affiche le message du tirage au sort et attend une interaction
	 * @param couleur Couleur
	 * @param nomJoueur Nom du joueur qui va jouer
	 * @param callback Bloc à exécuter lorsque l'interaction a été effectuée
	 */
	public void tirageAuSort(Couleur couleur, String nomJoueur, Runnable callback);
	
	/**
	 * Exécute un bloc lorsque l'interaction est effectuée
	 * @param callback Bloc à exécuter lorsque l'interaction a été effectuée
	 */
	public void attendreToucheEntrer(Runnable callback);
	
	/**
	 * Affiche le message de fin de partie quand un joueur a gagné
	 * @param numeroTour Numéro du tour en cours
	 * @param plateau Instance du plateau
	 * @param joueurGagnant Joueur gagnant
	 */
	public void finDePartie(int numeroTour, Plateau plateau, Joueur joueurGagnant);
	
	/**
	 * Arrêt d'une partie forcée
	 * @param plateau Instance du plateau
	 */
	public void stopPartie(Plateau plateau);
	
	/**
	 * Supprime les messages
	 */
	public void effacerAffichage();
	
	/**
	 * Met à jour l'interface
	 * @param plateau Instance du plateau
	 */
	public void miseAJourAffichage(Plateau plateau);
	
	/**
	 * Affiche un message à la suite
	 * @param msg Message
	 * @param color Couleur du message (Facultatif)
	 */
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
