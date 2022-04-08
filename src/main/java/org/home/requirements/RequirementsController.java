package org.home.requirements;

import java.util.ArrayList;
import java.util.List;

import org.home.requirements.model.Requirement;
import org.home.requirements.model.Stakeholder;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class RequirementsController {
	
	private static List<Stakeholder> stakeholders;
	private static List<Requirement> requirements;
	private static List<Pair<Stakeholder, Requirement>> links;
	
	public RequirementsController() {
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
	
	@GetMapping(path = "/stakeholder/list")
	public String listStakeholders(Model model) {		
		model.addAttribute("stakeholders", stakeholders);
		return "stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/{id}")
	public String viewStakeholder(@PathVariable Long id, Model model) {
		var stakeholder = stakeholders.stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("stakeholder", stakeholder);
		var requirements = links.stream()
							.filter(pair -> pair.getFirst() == stakeholder)
							.map(pair -> pair.getSecond());
		model.addAttribute("reqs", requirements.toList());
		model.addAttribute("messages", new ArrayList<String>());
		return "stakeholder/show";
	}
	
	@GetMapping(path = "/requirement/{id}")
	public String viewRequirement(@PathVariable Long id, Model model) {
		var requirement = requirements.stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("requirement", requirement);
		var stakeholders = links.stream()
							.filter(pair -> pair.getSecond() == requirement)
							.map(pair -> pair.getFirst());
		model.addAttribute("stakeholders", stakeholders.toList());
		model.addAttribute("messages", new ArrayList<String>());
		return "requirement/show";
	}
	
}
