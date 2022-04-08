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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
	
	private Requirement createRequirement(String name) {
		int nextId = requirements.stream()
							.max( (o1, o2) -> o1.getId() > o2.getId() ? 1 : -1)
							.map( x -> x.getId())
							.orElse(-1) + 1;
		var requirement = new Requirement(name, null, nextId);
		requirements.add(requirement);
		return requirement;
	}
	
	private static Stakeholder createStakeholder() {
		int nextId = stakeholders.stream()
							.max( (o1, o2) -> o1.getId() > o2.getId() ? 1 : -1)
							.map( x -> x.getId())
							.orElse(-1) + 1;
		var stakeholder = new Stakeholder(null, null, nextId);
		stakeholders.add(stakeholder);
		return stakeholder;
	}
	
	private static void createLink(Long requirementId, Stakeholder stakeholder) {
		var requirement = requirements.stream().filter(x ->  x.getId() == requirementId).findFirst().orElse(null);
		if(requirement != null && stakeholder != null) 
			links.add(Pair.of(stakeholder, requirement));
	}
		
	@GetMapping(path = "/")
	public String handleRoot() {
		return "redirect:stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/list")
	public String handleListStakeholders(Model model) {		
		model.addAttribute("stakeholders", stakeholders);
		return "stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/{id}")
	public String handleViewStakeholder(@PathVariable Long id, Model model) {
		var stakeholder = stakeholders.stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("stakeholder", stakeholder);
		var requirements = links.stream()
							.filter(pair -> pair.getFirst() == stakeholder)
							.map(pair -> pair.getSecond());
		model.addAttribute("reqs", requirements.toList());
		return "stakeholder/show";
	}
	
	@PutMapping(path = "/stakeholder/{id}")
	public String handleModifyStakeholder(
			@PathVariable Long id, 
			@RequestParam String stakeholder_name, 
			@RequestParam String stakeholder_description, Model model, RedirectAttributes redirectAttributes) {
		var stakeholder = stakeholders.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		if(stakeholder != null) {
			if(stakeholder_name != null && !stakeholder_name.equals(stakeholder.getName())) {
				stakeholder.setName(stakeholder_name);
				redirectAttributes.addFlashAttribute("messages",List.of("Information updated!"));
			}
			if(stakeholder_description != null && !stakeholder_description.equals(stakeholder.getDescription())) {
				stakeholder.setDescription(stakeholder_description);
				redirectAttributes.addFlashAttribute("messages",List.of("Information updated!"));
			}
		}
		return "redirect:/stakeholder/" + id;
	}
	
	@PostMapping(path = "/stakeholder") 
	public String handleCreateStakeholder(RedirectAttributes redirectAttributes) {
		var stakeholder = createStakeholder();
		if(stakeholder != null)
		{
		    redirectAttributes.addFlashAttribute("messages",List.of("Stakeholder created!"));
		    return "redirect:/stakeholder/" + stakeholder.getId() + "/edit";
		}
		redirectAttributes.addFlashAttribute("messages", List.of("Failed to create new stakeholder!"));
		return "redirect:/";
	}
	
	@PostMapping(path = "/stakeholder/{id}/requirement")
	public String handleNewRequirement(
			@PathVariable Long id, 
			@RequestParam(required = false) String requirement_name, 
			@RequestParam(required = false) Long requirement_id, Model model) {
		var stakeholder = stakeholders.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		if(stakeholder != null) {
			if(requirement_name != null)
				createRequirement(requirement_name);
			if(requirement_id != null)
				createLink(requirement_id, stakeholder);
		}
		return "redirect:/stakeholder/" + id;
	}
	
	@GetMapping(path = "/stakeholder/{id}/edit")
	public String handleEditStakeholder(@PathVariable Long id, Model model) {
		var stakeholder = stakeholders.stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("stakeholder", stakeholder);
		var linkedRequirements = links.stream()
							.filter(pair -> pair.getFirst() == stakeholder)
							.map(pair -> pair.getSecond())
							.toList();
		model.addAttribute("reqs", linkedRequirements);
		var linkable = new ArrayList<Requirement>();
		linkable.addAll(requirements);
		linkable.removeAll(linkedRequirements);

		model.addAttribute("linkable_reqs", linkable);
		return "stakeholder/edit"; 
	}
	
	@GetMapping(path = "/requirement/{id}/edit")
	public String handleEditRequirement(@PathVariable Long id, Model model) {
		var requirement = requirements.stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("requirement", requirement);
		return "requirement/edit";
	}
	
	@GetMapping(path = "/requirement/{id}")
	public String handleViewRequirement(@PathVariable Long id, Model model) {
		var requirement = requirements.stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("requirement", requirement);
		var stakeholders = links.stream()
							.filter(pair -> pair.getSecond() == requirement)
							.map(pair -> pair.getFirst());
		model.addAttribute("stakeholders", stakeholders.toList());
		return "requirement/show";
	}
	
	@PutMapping(path = "/requirement/{id}")
	public String handleModifyRequirement(
			@PathVariable Long id, 
			@RequestParam String requirement_name, 
			@RequestParam String requirement_description, Model model) {
		var requirement = requirements.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		if(requirement != null) {
			if(requirement_name != null && !requirement_name.equals(requirement.getName()))
				requirement.setName(requirement_name);
			if(requirement_description != null && !requirement_description.equals(requirement.getDescription()))
				requirement.setDescription(requirement_description);
		}
		return "redirect:/requirement/" + id;
	}
	
}
