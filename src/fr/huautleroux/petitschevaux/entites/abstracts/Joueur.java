package fr.huautleroux.petitschevaux.entites.abstracts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.huautleroux.petitschevaux.cases.CaseChemin;
import fr.huautleroux.petitschevaux.cases.CaseEchelle;
import fr.huautleroux.petitschevaux.cases.CaseEcurie;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.core.Plateau;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.enums.Couleur;
import fr.huautleroux.petitschevaux.enums.JoueurAction;

public abstract class Joueur {

	private transient List<Pion> chevaux = new ArrayList<Pion>();
	private transient Case caseDepart = null;
	private String nom;
	private Couleur couleur;

	public Joueur(String name, Couleur couleur) {
		this.nom = name;
		this.couleur = couleur;
	}

	public abstract JoueurAction choixAction(int de, Plateau plateau);
	public abstract Pion choisirPion(int de, JoueurAction choix, Plateau plateau);

	public boolean hasToutPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) == 4;
	}

	public boolean hasPionEcurie(Plateau plateau) {
		return getNombrePionEcurie(plateau) != 0;
	}

	public int getNombrePionEcurie(Plateau plateau) {
		return getPionsEcurie(plateau).size();
	}

	public List<Pion> getPionsEcurie(Plateau plateau) {
		return plateau.getEcuries().get(couleur.ordinal()).getChevaux();
	}

	protected List<Pion> getPionsParAction(JoueurAction action, Plateau plateau) {
		List<Pion> pionsAction = new ArrayList<Pion>();

		for (Pion pion : chevaux) {
			Case caseActuelle = pion.getCaseActuelle(plateau);

			if (action.equals(JoueurAction.SORTIR_CHEVAL) && caseActuelle instanceof CaseEcurie)
				pionsAction.add(pion);

			else if (action.equals(JoueurAction.DEPLACER_CHEVAL) && (caseActuelle instanceof CaseChemin || caseActuelle instanceof CaseEchelle))
				pionsAction.add(pion);
		}

		return pionsAction;
	}

	protected boolean estChoixValide(int de, int choix, Plateau plateau) {
		if(choix < 0 || choix >= JoueurAction.values().length) // Le choix n'existe pas
			return false;

		JoueurAction action = JoueurAction.values()[choix];
		boolean choixValide = true;

		if (action.equals(JoueurAction.SORTIR_CHEVAL) && !hasPionEcurie(plateau) // ucun cheval dans l'écurie
				|| action.equals(JoueurAction.SORTIR_CHEVAL) && de != 6 // Il n'a pas le droit d'en sortir un de l'écurie
				|| action.equals(JoueurAction.DEPLACER_CHEVAL) && hasToutPionEcurie(plateau)) // Aucun cheval sur le plateau
			choixValide = false;

		if(!choixValide)
			System.out.print("Votre choix n'est pas valide, reessayez : ");

		return choixValide;
	}
	
	public void ajouterCheval(Pion pion) {
		this.chevaux.add(pion);
		this.chevaux = this.chevaux.stream().sorted((p1, p2) -> p1.compareTo(p2)).collect(Collectors.toList());
	}

	public void initialisationReference() {
		this.chevaux = new ArrayList<Pion>();
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
