package fr.huautleroux.petitschevaux.utils.save;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;
import fr.huautleroux.petitschevaux.exceptions.ChargementSauvegardeException;
import fr.huautleroux.petitschevaux.exceptions.SauvegardeException;

public class SaveManager {

	private static final String EXT = ".json";
	private static final char[] invalideCaracteres = new char[] {' ', '/', '\\', '>', '<', ':', '|', '"', '?', '*'};

	private Gson gson;
	private File folder;

	/**
	 * Initialise la gestion des sauvegardes
	 * @param folderName Nom du sous dossier contenant les sauvegardes
	 */
	public SaveManager(String folderName) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Case.class, new InterfaceAdapter<Case>());
		gsonBuilder.registerTypeAdapter(CaseColoree.class, new InterfaceAdapter<CaseColoree>());
		gsonBuilder.registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>());
		gsonBuilder.setPrettyPrinting();
		this.gson = gsonBuilder.create();

		this.folder = new File(folderName);
		if(!folder.exists())
			folder.mkdir();
	}

	/**
	 * Charge une sauvegarde (fichier) à partir de son nom
	 * @param saveName Nom du fichier
	 * @return Partie
	 * @throws ChargementSauvegardeException Erreur générée si le chargement échoue
	 */
	public Partie chargerPartie(String saveName) throws ChargementSauvegardeException {
		saveName = convertSaveName(saveName);
		File saveFile = getFile(saveName);

		if(!saveFile.exists())
			throw new ChargementSauvegardeException("La sauvegarde " + saveName + " n'existe pas");

		String json;

		try {
			json = new String(Files.readAllBytes(Paths.get(saveFile.toURI())));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ChargementSauvegardeException("Une erreur de lecture est survenue pour la sauvegarde " + saveName);
		}

		try {
			Partie partie = gson.fromJson(json, Partie.class);
			partie.initialiserReference();
			return partie;
		} catch(JsonSyntaxException e) {
			e.printStackTrace();
			throw new ChargementSauvegardeException("La sauvegarde " + saveName + " n'est pas valide");
		}
	}

	/**
	 * Vérifie si le fichier ciblé est bien un fichier de sauvegarde, et qu'elle est compatible avec la version actuelle
	 * @param saveName Nom du fichier
	 * @return boolean
	 */
	public boolean estSauvegardeValide(String saveName) {
		saveName = convertSaveName(saveName);
		try {
			chargerPartie(saveName);
			return true;
		} catch(ChargementSauvegardeException e) {
			return false;
		}
	}

	/**
	 * Sauvegarde une partie en un fichier
	 * @param partie Partie qui sera sauvegardée
	 * @param saveName Nom du (futur) fichier
	 * @param overwrite Si l'on doit écraser le fichier s'il existe déjà
	 * @throws SauvegardeException Erreur générée si la sauvegarde échoue
	 */
	public void sauvegarderPartie(Partie partie, String saveName, boolean overwrite) throws SauvegardeException {
		saveName = convertSaveName(saveName);
		File saveFile = getFile(saveName);

		if(saveFile.exists() && !overwrite)
			throw new SauvegardeException("La sauvegarde " + saveName + " existe déjà");

		try {
			Files.write(Paths.get(saveFile.toURI()), gson.toJson(partie).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new SauvegardeException("Une erreur d'écriture est survenue pour la sauvegarde " + saveName);
		}
	}

	/**
	 * Liste les sauvegardes compatibles
	 * @return List&lt;String&gt; Liste des noms de fichiers de sauvegardes compatibles
	 */
	public List<String> getSauvegardes() {
		List<String> saves = Arrays.asList(folder.list()).stream().map(save -> {
			int index = save.lastIndexOf(EXT);
			if(index != -1)
				save = save.replace(save.substring(index, save.length()), "");
			
			return save;
		}).collect(Collectors.toList()).stream().filter(save -> estSauvegardeValide(save)).collect(Collectors.toList());

		return saves;
	}
	
	/**
	 * Converti un nom de fichier, en retirant des caractères interdits
	 * @param saveName Nom du fichier
	 * @return String Nom du fichier converti
	 */
	public String convertSaveName(String saveName) {
		for (char c : invalideCaracteres)
			saveName = saveName.replace(c, '_');
		
		return saveName;
	}

	private File getFile(String saveName) {
		return new File(folder + File.separator + saveName + EXT);
	}
}
