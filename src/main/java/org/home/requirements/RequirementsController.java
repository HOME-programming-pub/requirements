package org.home.requirements;

import org.home.requirements.model.RequirementsModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequirementsController {
	
	private RequirementsModel data = RequirementsModel.getInstance();	
	
	@GetMapping(path = "/requirement/{id}/edit")
	public String handleEditRequirement(@PathVariable Long id, Model model) {
		var requirement = data.getRequirements().stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("requirement", requirement);
		return "requirement/edit";
	}
	
	@GetMapping(path = "/requirement/{id}")
	public String handleViewRequirement(@PathVariable Long id, Model model) {
		var requirement = data.getRequirements().stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("requirement", requirement);
		var stakeholders = data.getLinks().stream()
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
		var requirement = data.getRequirements().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		if(requirement != null) {
			if(requirement_name != null && !requirement_name.equals(requirement.getName()))
				requirement.setName(requirement_name);
			if(requirement_description != null && !requirement_description.equals(requirement.getDescription()))
				requirement.setDescription(requirement_description);
		}
		return "redirect:/requirement/" + id;
	}
	
}
