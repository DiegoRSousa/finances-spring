package com.diego.finances.model;

public enum Type {

	INCOMES("Incomes"), 
	EXPENSES("Expenses");
	
	private String description;
	
	private Type(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
