package fr.huautleroux.petitschevaux.entites.abstracts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;
import fr.huautleroux.petitschevaux.exceptions.AucunPionException;

public abstract class Joueur implements Comparable<Joueur> {

	private transient List<Pion> chevaux;
	private transient Case caseDepart = null;
	private String nom;
	private Couleur couleur;

	public Joueur(String nom, Couleur couleur) {
		this.nom = nom;
		this.couleur = couleur;
	}

	/**
	 * Permet au joueur de choisir une action parmis les actions possibles avec son lancé de dé
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 * @param plateau Instance du plateau
	 * @return Action choisie par le joueur
	 */
	public abstract JoueurAction choixAction(int de, Plateau plateau);
	
	/**
	 * Permet au joueur de choisir un pion parmis l'action qu'il a choisi
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 * @param choix Choix choisi par le joueur
	 * @param plateau Instance du plateau
	 * @return Pion choisi par le joueur
	 */
	public abstract Pion choisirPion(int de, JoueurAction choix, Plateau plateau) throws AucunPionException;

	/**
	 * @param plateau Instance du plateau
	 * @return Si tous les chevaux du joueur sont dans l'écurie
	 */
	public boolean hasToutPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) == 4;
	}

	/**
	 * @param plateau Instance du plateau
	 * @return Si au moins un des chevaux du joueur sont dans l'écurie
	 */
	public boolean hasPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) != 0;
	}

	/**
	 * @param plateau Instance du plateau
	 * @return Le nombre de chevaux du joueur qui sont dans l'écurie
	 */
	public int getNombrePionEcurie(Plateau plateau) {
		return getPionsEcurie(plateau).size();
	}

	/**
	 * @param plateau Instance du plateau
	 * @return Liste des chevaux du joueur qui sont dans l'écurie
	 */
	public List<Pion> getPionsEcurie(Plateau plateau) {
		return plateau.getEcuries().get(couleur.ordinal()).getChevaux();
	}
	
	/**
	 * Ajoute un pion au joueur
	 * @param pion Pion à ajouter
	 */
	public void ajouterCheval(Pion pion) {
		this.chevaux.add(pion);
		this.chevaux = this.chevaux.stream().sorted((p1, p2) -> p1.compareTo(p2)).collect(Collectors.toList());
	}

	/**
	 * Initialise la liste de chevaux
	 */
	public void initialisationChevaux() {
		this.chevaux = new ArrayList<Pion>();
	}

	/**
	 * @param de Nombre de déplacements que le cheval doit effectuer
	 * @param plateau Instance du plateau
	 * @return Liste d'actions disponibles pour un tirage de dé
	 */
	protected List<JoueurAction> getActionsDisponible(int de, Plateau plateau) {
		List<JoueurAction> actionsDispo = new ArrayList<JoueurAction>(Arrays.asList(JoueurAction.RIEN_FAIRE, JoueurAction.SAUVEGARDER));

		if(hasToutPionEcurie(plateau)) {
			if(de == 6)
				actionsDispo.add(1, JoueurAction.SORTIR_CHEVAL);
		}

		else {
			if(de == 6 && hasPionEcurie(plateau))
				actionsDispo.add(1, JoueurAction.SORTIR_CHEVAL);

			actionsDispo.add(actionsDispo.size() - 1, JoueurAction.DEPLACER_CHEVAL);
		}

		return actionsDispo;
	}
	
	/**
	 * @param action Action choisie par le joueur
	 * @return Liste des pions qui sont compatibles avec l'action choisie
	 */
	protected List<Pion> getPionsParAction(JoueurAction action) {
		List<Pion> pionsAction = new ArrayList<Pion>();

		for (Pion pion : chevaux) {
			Case caseActuelle = pion.getCaseActuelle();

			if (action.equals(JoueurAction.SORTIR_CHEVAL) && caseActuelle instanceof CaseEcurie)
				pionsAction.add(pion);

			else if (action.equals(JoueurAction.DEPLACER_CHEVAL) && !(caseActuelle instanceof CaseEcurie))
				pionsAction.add(pion);
		}

		return pionsAction;
	}
	
	@Override
	public String toString() {
		return getNom() + " (" + getCouleur() + ")";
	}
	
	@Override
	public int compareTo(Joueur joueur) {
		return Integer.valueOf(this.getCouleur().ordinal()).compareTo(Integer.valueOf(joueur.getCouleur().ordinal()));
	}

	public Case getCaseDeDepart() {
		return caseDepart;
	}

	public void setCaseDeDepart(Case caseDepart) {
		this.caseDepart = caseDepart;
	}

	public List<Pion> getChevaux() {
		return chevaux;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String name) {
		this.nom = name;
	}

	public Couleur getCouleur() {
		return couleur;
	}
}
