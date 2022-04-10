package org.home.requirements;

import org.home.requirements.model.RequirementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequirementsController {
	
	@Autowired private RequirementRepository data;
	
	@GetMapping(path = "/requirement/{id}/edit")
	public String handleEditRequirement(@PathVariable Integer id, Model model) {
		var requirement = data.findById(id).get();
		model.addAttribute("requirement", requirement);
		return "requirement/edit";
	}
	
	@GetMapping(path = "/requirement/{id}")
	public String handleViewRequirement(@PathVariable Integer id, Model model) {
		var requirement = data.findById(id).get();
		model.addAttribute("requirement", requirement);
		var stakeholders = requirement.getStakeholders();
		model.addAttribute("stakeholders", stakeholders);
		return "requirement/show";
	}
	
	@PutMapping(path = "/requirement/{id}")
	public String handleModifyRequirement(
			@PathVariable Integer id, 
			@RequestParam String requirement_name, 
			@RequestParam String requirement_description, Model model) {
		var requirement = data.findById(id).get();
		if(requirement != null) {
			if(requirement_name != null && !requirement_name.equals(requirement.getName()))
				requirement.setName(requirement_name);
			if(requirement_description != null && !requirement_description.equals(requirement.getDescription()))
				requirement.setDescription(requirement_description);
			data.save(requirement);
		}
		return "redirect:/requirement/" + id;
	}
	
}
