package org.home.requirements.model;

public class Requirement {
	private String name;
	private String description;
	
	private int id;
	
	public Requirement(String name, String description, int id) {
		this.name = name;
		this.description = description;
		this.id = id;
	}
	
	public String getName() { return name; }	
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String desc) { this.description = desc; }

	public int getId() {
		return id;
	}


}
