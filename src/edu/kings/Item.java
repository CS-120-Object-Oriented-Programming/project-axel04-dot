package edu.kings;

public class Item {
	
	private String name;
	private String description;
	private Integer weight;
	private Integer value;
	
	
	
	public Item(String name, String description, Integer weight, Integer value) {
	    this.name = name;
	    this.description = description;
	    this.weight = weight;
	    this.value = value;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight=weight;
	}
	public int getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value=value;
	}
	
	public String toString() {
		return "Item: " + name + "\nDescription: " + description + "\nWeight: " + weight + " lbs";
	}
	
}