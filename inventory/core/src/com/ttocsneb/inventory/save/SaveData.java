package com.ttocsneb.inventory.save;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * @author Ben Jacobs
 *
 *         This class contains objects that will be saved offline.
 *
 */
public class SaveData {
	private final String SAVEID = "Inventory-ttocsneb.pref";
	private Preferences PREFS;

	public void save() {
		if (PREFS == null) PREFS = Gdx.app.getPreferences(SAVEID);
		Json json = new Json();

		// Convert the Map into an array to store the values.
		Item[] tmp = new Item[stock.values().size()];
		stock.values().toArray(tmp);
		PREFS.putString(stockID, json.toJson(tmp));

		PREFS.putString(orderID, json.toJson(orders));
		PREFS.flush();
	}

	@SuppressWarnings("unchecked") public void load() {
		if (PREFS == null) PREFS = Gdx.app.getPreferences(SAVEID);
		Json json = new Json();
		// Load the Stock
		if (stock == null) stock = new HashMap<Short, Item>();
		else stock.clear();
		if (PREFS.contains(stockID)) {
			
			for (Item i : json.fromJson(Item[].class, PREFS.getString(stockID))) {
				stock.put(i.id, i);
			}
		}

		if (PREFS.contains(orderID)) orders = json.fromJson(Array.class, PREFS.getString(orderID));
		else orders = new Array<Order>();
	}

	////////////// Stock Items/////////////////

	/**
	 * Your stock
	 */
	public Map<Short, Item> stock;
	private static final String stockID = "Stock";

	//////////// Orders //////////////

	public Array<Order> orders;
	private static final String orderID = "Orders";

}
