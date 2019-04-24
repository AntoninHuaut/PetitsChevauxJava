package fr.huautleroux.petitschevaux;

import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
import fr.huautleroux.petitschevaux.utils.Saisie;
import fr.huautleroux.petitschevaux.utils.save.SaveManager;

public class PetitsChevaux {

	private static PetitsChevaux instance;
	private transient Partie partie;
	private transient SaveManager saveManager;

	public PetitsChevaux() {
		instance = this;
		this.saveManager = new SaveManager("saves");

		try {
			this.partie = menuChargementSauvegarde();
		} catch (ChargementSauvegardeException e) {
			System.out.println("Nouvelle partie\n");
			this.partie = new Partie();
			this.partie.initialiserJeu();
		}

		this.partie.startJeu();
	}

	public Partie menuChargementSauvegarde() throws ChargementSauvegardeException {
		if(saveManager.getSauvegardes().isEmpty())
			throw new ChargementSauvegardeException("Aucune sauvegarde n'existe");
		
		System.out.println("Souhaitez-vous charger une sauvegarde ? (Oui/Non)");
		boolean chargerSauvegarde = Saisie.asBoolean();

		if(!chargerSauvegarde)
			throw new ChargementSauvegardeException("Opération interrompue");

		System.out.println(" Liste des sauvegardes :");
		saveManager.getSauvegardes().forEach(save -> System.out.println("    • " + save));

		String nomSauvegarde;

		do {
			System.out.print("Choisissez la sauvegarde à charger (Tappez stop pour annuler) : ");
			nomSauvegarde = Saisie.asStringNoEmpty();
			System.out.println("");
		} while(!saveManager.estSauvegardeValide(nomSauvegarde) && !nomSauvegarde.equals("stop"));

		if(nomSauvegarde.equals("stop"))
			throw new ChargementSauvegardeException("Opération interrompue");

		nomSauvegarde = saveManager.convertSaveName(nomSauvegarde);

		Partie partie = saveManager.chargerPartie(nomSauvegarde);
		System.out.println("La partie " + nomSauvegarde + " a été chargée\n");
		return partie;
	}

	public static void main(String[] args) {
		new PetitsChevaux();
	}

	public SaveManager getSaveManager() {
		return saveManager;
	}

	public static PetitsChevaux getInstance() {
		return instance;
	}
}
