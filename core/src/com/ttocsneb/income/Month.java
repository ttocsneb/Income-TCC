package com.ttocsneb.income;

public enum Month {

	JAN("Jan", 1),
	FEB("Feb", 2),
	MAR("Mar", 3),
	APR("Apr", 4),
	MAY("May", 5),
	JUN("Jun", 6),
	JUL("Jul", 7),
	AUG("Aug", 8),
	SEP("Sep", 9),
	OCT("Oct", 10),
	NOV("Nov", 11),
	DEC("Dec", 12);
	
	private String Name;
	private int Month;
	
	private Month(String name, int month) {
		Name = name;
		Month = month;
	}
	
	/**
	 * Get a number representation of the current month.
	 * @return month
	 */
	public int getMonth() {
		return Month;
	}
	
	@Override
	public String toString() {
		return Name;
	}
	
}
