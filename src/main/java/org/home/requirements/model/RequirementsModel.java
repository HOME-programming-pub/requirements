package org.home.requirements.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.util.Pair;

public class RequirementsModel {
	
	private List<Stakeholder> stakeholders;
	private List<Requirement> requirements;
	private List<Pair<Stakeholder, Requirement>> links;
    
	private RequirementsModel() {
		stakeholders = new ArrayList<Stakeholder>();
		stakeholders.add(new Stakeholder("Driver", "The driver of the Fantasy car.", 1000));
		stakeholders.add(new Stakeholder("Owner", "The owner/buyer of the Fantasy car.", 1001));
		stakeholders.add(new Stakeholder("Mechanic", "Mechanical engineer who maintains a car.", 1002));
		stakeholders.add(new Stakeholder("Upper management", "The management of our business.", 1003));
		stakeholders.add(new Stakeholder("Legal department", "The legal department of Fantasy Automotive.", 1004));
		requirements = new ArrayList<Requirement>();
		requirements.add(new Requirement("Operability at highway speeds.", "The system shall be operable at normal highway speeds.", 2001));
		requirements.add(new Requirement("Conformance to ISO 26262.", "The driving function shall conform to the safety rules in ISO 26262.", 2002));
		links = new ArrayList<Pair<Stakeholder, Requirement>>();
		links.add(Pair.of(stakeholders.get(0), requirements.get(0)));
	}
	
	public List<Stakeholder> getStakeholders() {
		return stakeholders;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public List<Pair<Stakeholder, Requirement>> getLinks() {
		return links;
	}

	public void add(Stakeholder sh) {
		stakeholders.add(sh);
	}
	
	public void add(Requirement req) {
		requirements.add(req);
	}
	
	public void addLink(Stakeholder sh, Requirement req) {
		links.add(Pair.of(sh, req));
	}
	
	public Requirement createRequirement(String name) {
		int nextId = requirements.stream()
							.max( (o1, o2) -> o1.getId() > o2.getId() ? 1 : -1)
							.map( x -> x.getId())
							.orElse(-1) + 1;
		var requirement = new Requirement(name, null, nextId);
		this.add(requirement);
		return requirement;
	}
	
	public Stakeholder createStakeholder() {
		int nextId = stakeholders.stream()
							.max( (o1, o2) -> o1.getId() > o2.getId() ? 1 : -1)
							.map( x -> x.getId())
							.orElse(-1) + 1;
		var stakeholder = new Stakeholder(null, null, nextId);
		this.add(stakeholder);
		return stakeholder;
	}
	
	public void createLink(Long requirementId, Stakeholder stakeholder) {
		var requirement = requirements.stream()
								.filter(x ->  x.getId() == requirementId)
								.findFirst()
								.orElse(null);
		if(requirement != null && stakeholder != null) 
			links.add(Pair.of(stakeholder, requirement));
	}
	
	// See https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Holder {
        private static final RequirementsModel INSTANCE = new RequirementsModel();
    }
    
    public static RequirementsModel getInstance() {
        return Holder.INSTANCE;
    }
	
}
