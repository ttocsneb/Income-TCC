package com.ttocsneb.inventory.save;

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
