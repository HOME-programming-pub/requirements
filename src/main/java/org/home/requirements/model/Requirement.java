package org.home.requirements.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Requirement {
	private String name;
	private String description;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id private int id;
	
	@ManyToMany(mappedBy="requirements")
    private Set<Stakeholder> stakeholders = new HashSet<Stakeholder>();
	
	public Requirement() {};
	
	public Requirement(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public String getName() { return name; }	
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String desc) { this.description = desc; }

	public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Set<Stakeholder> getStakeholders() {
    	return this.stakeholders;
    }
    
} 
