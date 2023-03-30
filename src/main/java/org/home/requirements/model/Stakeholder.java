package org.home.requirements.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Stakeholder {
	private String name;
	private String description;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id private int id;
	
	
    @ManyToMany
    private Set<Requirement> requirements = new HashSet<Requirement>();
	
    public Stakeholder() {}
    
	public Stakeholder(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; };
	
	public Set<Requirement> getRequirements() {
		return this.requirements;
	}
	
    public void addRequirement(Requirement req) {
    	this.requirements.add(req);
    	req.getStakeholders().add(this);
    }
}
