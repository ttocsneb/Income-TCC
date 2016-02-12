package com.ttocsneb.income.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class Save {
	// The transactions for the save.
	public Array<Transaction> transactions;

	// ////////////// Static functions ////////////////////

	// Setup the save file.
	private static final String SAVE = "SaveData";
	private static Preferences prefs = Gdx.app.getPreferences("TCCIncome");

	// An internal save object used for saving the data.
	private static Save save;

	/**
	 * Load the save from a previous runtime.
	 * 
	 * @return the requested save
	 */
	public static Save load() {
		// Create a Json to interpret our save file.
		Json json = new Json();
		// load the save.
		save = json.fromJson(Save.class, prefs.getString(SAVE, "{}"));
		// check that the save is valid, and validate it.
		if (save == null) {
			save = new Save();
			save.transactions = new Array<Transaction>();
		} else if (save.transactions == null) {
			save.transactions = new Array<Transaction>();
		}
		// return the save.
		return save;
	}

	/**
	 * Store the save for future use.
	 */
	public static void save() {
		// Create a Json to convert our class to a saveable format i.e. a String
		Json json = new Json();
		// Create a Save if it does not yet exist.
		if (save == null) {
			save = new Save();
			save.transactions = new Array<Transaction>();
		}
		// save the class.
		prefs.putString(SAVE, json.toJson(save));
		prefs.flush();
	}
}
