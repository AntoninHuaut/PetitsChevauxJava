package fr.huautleroux.petitschevaux.entites.abstracts;

import java.util.ArrayList;
import java.util.Arrays;
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
import fr.huautleroux.petitschevaux.exceptions.AucunPionException;

public abstract class Joueur {

	private transient List<Pion> chevaux;
	private transient Case caseDepart = null;
	private String nom;
	private Couleur couleur;

	public Joueur(String nom, Couleur couleur) {
		this.nom = nom;
		this.couleur = couleur;
	}

	public abstract JoueurAction choixAction(int de, Plateau plateau);
	public abstract Pion choisirPion(int de, JoueurAction choix, Plateau plateau) throws AucunPionException;

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
	
	public void ajouterCheval(Pion pion) {
		this.chevaux.add(pion);
		this.chevaux = this.chevaux.stream().sorted((p1, p2) -> p1.compareTo(p2)).collect(Collectors.toList());
	}

	public void initialisationReference() {
		this.chevaux = new ArrayList<Pion>();
	}

	protected List<JoueurAction> getActionDisponible(int de, Plateau plateau, boolean afficherMessage) {
		List<JoueurAction> actionDispo = new ArrayList<JoueurAction>(Arrays.asList(JoueurAction.RIEN_FAIRE, JoueurAction.SAUVEGARDER));

		if (afficherMessage)
			System.out.println("  Vous pouvez ne rien faire [0]");

		if(hasToutPionEcurie(plateau)) {
			if(de == 6) {
				actionDispo.add(JoueurAction.SORTIR_CHEVAL);
				if (afficherMessage)
					System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");
			}
		}

		else {
			if(de == 6 && hasPionEcurie(plateau)) {
				actionDispo.add(JoueurAction.SORTIR_CHEVAL);
				if (afficherMessage)
					System.out.println("  Vous pouvez sortir un cheval de l'écurie [1]");
			}

			actionDispo.add(JoueurAction.DEPLACER_CHEVAL);
			if (afficherMessage)
				System.out.println("  Vous pouvez déplacer un cheval sur le plateau [2]");
		}

		if (afficherMessage)
			System.out.println("  Vous pouvez sauvegarder [3]");

		return actionDispo;
	}

	protected List<Pion> getPionsParAction(JoueurAction action) {
		List<Pion> pionsAction = new ArrayList<Pion>();

		for (Pion pion : chevaux) {
			Case caseActuelle = pion.getCaseActuelle();

			if (action.equals(JoueurAction.SORTIR_CHEVAL) && caseActuelle instanceof CaseEcurie)
				pionsAction.add(pion);

			else if (action.equals(JoueurAction.DEPLACER_CHEVAL) && (caseActuelle instanceof CaseChemin || caseActuelle instanceof CaseEchelle))
				pionsAction.add(pion);
		}

		return pionsAction;
	}

	protected boolean estChoixValide(int choix, List<JoueurAction> actionDispo) {
		if(choix < 0 || choix >= JoueurAction.values().length)
			return false;

		return actionDispo.contains(JoueurAction.values()[choix]);
	}
	
	@Override
	public String toString() {
		return getNom() + " (" + getCouleur() + ")";
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
