package com.ttocsneb.inventory.save;

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
	public static final SaveData SAVE = new SaveData();
	
	private static final Preferences PREFS = Gdx.app.getPreferences("Ttocsneb.Inventory");


	private SaveData() {
		load();
	}

	public void save() {
		Json json = new Json();
		PREFS.putString(stockID, json.toJson(stock));
		PREFS.putString(orderID, json.toJson(orders));
		PREFS.flush();
	}

	@SuppressWarnings("unchecked") public void load() {
		Json json = new Json();
		stock = json.fromJson(Map.class, PREFS.getString(stockID));
		orders = json.fromJson(Array.class, PREFS.getString(orderID));
	}

	////////////// Stock Items/////////////////

	/**
	 * Your stock
	 */
	public Map<Short, Item> stock;
	private static final String stockID = "Stock";

	/**
	 * An Item is an object that contains information for Stock that you can
	 * sell.<br>
	 * 
	 * Including:
	 * <ul>
	 * <li>Name</li>
	 * <li>Weight</li>
	 * <li>price</li>
	 * <li>stock</li>
	 * </ul>
	 */
	public class Item {

		public String name;
		public float weight;
		public float price;
		public float amount;
		public short id;

	}

	//////////// Orders //////////////

	public Array<Order> orders;
	private static final String orderID = "Orders";

	/**
	 * An order containing items purchased, address, etc. TODO
	 */
	public class Order {

		public OrderItem[] orders;

		public String address;

		/**
		 * Contains data for individual items purchased, and their prices.
		 */
		public class OrderItem {
			public short id;// product ID
			public int amount;// amount purchased
			public float price;// total
		}

	}

}
