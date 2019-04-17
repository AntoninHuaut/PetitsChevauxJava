package fr.huautleroux.petitschevaux.utils;

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

	private Gson gson;
	private File folder;

	public SaveManager(String folderName) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Joueur.class, new InterfaceAdapter<Joueur>());
		gsonBuilder.registerTypeAdapter(Case.class, new InterfaceAdapter<Case>());
		gsonBuilder.registerTypeAdapter(CaseColoree.class, new InterfaceAdapter<CaseColoree>());
		gsonBuilder.setPrettyPrinting();
		this.gson = gsonBuilder.create();

		this.folder = new File(folderName);
		if(!folder.exists())
			folder.mkdir();
	}

	public Partie chargerPartie(String saveName) throws ChargementSauvegardeException {
		File saveFile = getFile(saveName);

		if(!saveFile.exists())
			throw new ChargementSauvegardeException("La sauvegarde " + saveName + " n'existe pas");

		String json;

		try {
			json = new String(Files.readAllBytes(Paths.get(saveFile.toURI())));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ChargementSauvegardeException("Une erreur d'écriture est survenue pour la sauvegarde " + saveName);
		}

		try {
			return gson.fromJson(json, Partie.class);
		} catch(JsonSyntaxException e) {
			e.printStackTrace();
			throw new ChargementSauvegardeException("La sauvegarde " + saveName + " n'est pas valide");
		}
	}

	public boolean estSauvegardeValide(String saveName) {
		try {
			chargerPartie(saveName);
			return true;
		} catch(ChargementSauvegardeException e) {
			return false;
		}
	}

	public boolean sauvegarderPartie(Partie partie, String saveName, boolean overwrite) throws SauvegardeException {
		File saveFile = getFile(saveName);

		if(saveFile.exists() && !overwrite)
			throw new SauvegardeException("La sauvegarde " + saveName + " existe déjà");

		try {
			Files.write(Paths.get(saveFile.toURI()), gson.toJson(partie).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new SauvegardeException("Une erreur de lecture est survenue pour la sauvegarde " + saveName);
		}

		return true;
	}

	public boolean sauvegarderPartie(Partie partie, String saveName) throws SauvegardeException {
		return sauvegarderPartie(partie, saveName, true);
	}

	public List<String> getSauvegardes() {
		List<String> saves = Arrays.asList(folder.list()).stream().map(save -> {
			int index = save.lastIndexOf(EXT);
			if(index != -1)
				save = save.replace(save.substring(index, save.length()), "");
			
			return save;
		}).collect(Collectors.toList()).stream().filter(save -> estSauvegardeValide(save)).collect(Collectors.toList());

		return saves;
	}

	private File getFile(String saveName) {
		return new File(folder + File.separator + saveName + EXT);
	}
}
