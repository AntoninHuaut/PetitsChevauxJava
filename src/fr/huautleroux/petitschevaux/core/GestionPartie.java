package fr.huautleroux.petitschevaux.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import fr.huautleroux.petitschevaux.Main;
import fr.huautleroux.petitschevaux.affichage.AffichageInterface;
import fr.huautleroux.petitschevaux.affichage.console.IConsole;
import fr.huautleroux.petitschevaux.affichage.graphique.IGraphique;
import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.entites.JoueurBot;
import fr.huautleroux.petitschevaux.entites.JoueurHumain;
import fr.huautleroux.petitschevaux.entites.Pion;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.enums.Couleur;

public class GestionPartie {

	private Partie partie = null;
	private AffichageInterface affichageInterface;
	
	public GestionPartie(AffichageInterface affichageInterface) {
		this.affichageInterface = affichageInterface;
	}
	
	/**
	 * Démarre ou charge une (nouvelle) partie, l'initialise et la lance
	 * @param nouvellePartie Indique si on doit démarrer une nouvelle partie
	 */
	public void demarrerPartie(boolean nouvellePartie) {
		if (nouvellePartie)
			setPartie(new Partie());
		
		if (nouvellePartie)
			initialiserJeu((p) -> p.jouerJeu());
		else {
			initialiserReference();
			partie.jouerJeu();
		}
	}

	/**
	 * Gère l'initialisation complète d'une partie
	 * @param callback Bloc à exécuter lorsque l'initialisation est terminée
	 */
	public void initialiserJeu(Consumer<Partie> callback) {
		partie.setCouleurCommence(tirageCouleur());
		int nbJoueurHumain;
		
		if (Main.utilise_Interface())
			nbJoueurHumain = toGraphique().getPopup().getNombres(4, "Nouvelle partie : Configuration", "Le nombre de joueurs doit être compris en 0 et 4", "Nombre de joueurs : ");
		else
			nbJoueurHumain = toConsole().getNombreJoueurs();

		initialiserJoueurs(nbJoueurHumain, () -> {
			initialiserPlateau();
			initialiserReference();
			callback.accept(partie);
		});
	}

	/**
	 * Initialise les joueurs (humains et bots) d'une partie
	 * @param nbJoueurHumain Nombre de joueurs humains
	 * @param callback Bloc à exécuter lorsque l'initialisation est terminée
	 */
	public void initialiserJoueurs(int nbJoueurHumain, Runnable callback) {
		int nbJoueurBot = 4 - nbJoueurHumain;
		
		if (nbJoueurHumain > 0) {
			HashMap<String, Couleur> pairs;
			
			if (Main.utilise_Interface())
				pairs = toGraphique().getPopup().getInitialisationJoueurs(nbJoueurHumain);
			else
				pairs = toConsole().getInitialisationJoueurs(nbJoueurHumain);

			for (String nomJoueur : pairs.keySet())
				partie.getJoueurs().add(new JoueurHumain(nomJoueur, pairs.get(nomJoueur)));
		}

		for (int i = 0; i < nbJoueurBot; i++) {
			List<Couleur> couleurs = new ArrayList<Couleur>(Arrays.asList(Couleur.values()));
			partie.getJoueurs().forEach(j -> couleurs.remove(j.getCouleur()));

			if (couleurs.isEmpty())
				return;

			partie.getJoueurs().add(new JoueurBot(couleurs.get(0)));
		}

		partie.trierOrdreJoueurs();
		callback.run();
	}

	/**
	 * Initialise le plateau de la partie
	 */
	public void initialiserPlateau() {
		partie.setPlateau(new Plateau());

		for(int idJoueur = 0; idJoueur < partie.getJoueurs().size(); idJoueur++)
			for(int idCheval = 0; idCheval < 4; idCheval++) {
				Pion pion = new Pion(idCheval, Couleur.values()[idJoueur]);
				partie.getPlateau().getEcuries().get(idJoueur).ajouteCheval(pion);
			}
	}

	/**
	 * Initialise des variables d'instances par des objets déjà existants dans d'autres instances
	 */
	public void initialiserReference() {
		partie.getPlateau().setPartie(partie);
		partie.setGererPartie(this);

		List<Case> cases = new ArrayList<Case>();
		cases.addAll(partie.getPlateau().getEcuries());
		cases.addAll(partie.getPlateau().getChemin());
		partie.getPlateau().getEchelles().forEach(c -> cases.addAll(c));

		for(int idJoueur = 0; idJoueur < partie.getJoueurs().size(); idJoueur++) {
			Joueur j = partie.getJoueurs().get(idJoueur);
			j.setCaseDeDepart(partie.getPlateau().getChemin().get(1 + idJoueur * 14));
			j.initialisationChevaux();

			final int idJoueurFinal = idJoueur;
			cases.forEach(c -> {
				if (c instanceof CaseColoree && ((CaseColoree) c).getCouleur().ordinal() != idJoueurFinal)
					return;

				c.getChevaux().forEach(pion -> {
					if (pion.getCouleur().equals(j.getCouleur()))
						j.ajouterCheval(pion);
				});
			});
		}

		cases.forEach(c -> c.getChevaux().forEach(pion -> pion.setCaseActuelle(c)));
	}
	
	/**
	 * Définit la partie qui va être utilisée
	 * @param partie Partie
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	/**
	 * Tire une couleur au hasard
	 * @return Couleur
	 */
	private Couleur tirageCouleur(){
		int de = partie.getRandom().nextInt(4);
		Couleur[] couleurs = Couleur.values();
		return couleurs[de];
	}
	
	public IConsole toConsole() {
		return (IConsole) affichageInterface;
	}
	
	public IGraphique toGraphique() {
		return (IGraphique) affichageInterface;
	}

	public AffichageInterface getInterface() {
		return affichageInterface;
	}
}
