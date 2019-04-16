package fr.huautleroux.petitschevaux.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.huautleroux.petitschevaux.cases.abstracts.Case;
import fr.huautleroux.petitschevaux.cases.abstracts.CaseColoree;
import fr.huautleroux.petitschevaux.core.Partie;
import fr.huautleroux.petitschevaux.entites.abstracts.Joueur;

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

	public Partie chargerPartie(String saveName) {
		File saveFile = getFile(saveName);

		if(!saveFile.exists())
			return null;

		String json;

		try {
			json = new String(Files.readAllBytes(Paths.get(saveFile.toURI())));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return gson.fromJson(json, Partie.class);
	}

	public boolean sauvegarderPartie(Partie partie, String saveName, boolean overwrite) {
		File saveFile = getFile(saveName);

		if(saveFile.exists() && !overwrite)
			return false;

		try {
			Files.write(Paths.get(saveFile.toURI()), gson.toJson(partie).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean sauvegarderPartie(Partie partie, String saveName) {
		return sauvegarderPartie(partie, saveName, true);
	}
	
	public List<String> getSauvegardes() {
		List<String> saves = Arrays.asList(folder.list());
		for(int i = 0; i < saves.size(); i++) {
			String save = saves.get(i);
			saves.set(i, save.replace(save.substring(save.lastIndexOf(EXT), save.length()), ""));
		}
		
		return saves;
	}

	private File getFile(String saveName) {
		return new File(folder + File.separator + saveName + EXT);
	}
}
