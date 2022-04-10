package org.home.requirements;

import java.util.ArrayList;
import java.util.List;

import org.home.requirements.model.Requirement;
import org.home.requirements.model.RequirementRepository;
import org.home.requirements.model.Stakeholder;
import org.home.requirements.model.StakeholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StakeholderController {
	
	@Autowired private StakeholderRepository data;	
	@Autowired private RequirementRepository reqData;	
	
	@GetMapping(path = "/")
	public String handleRoot() {
		return "redirect:stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/list")
	public String handleListStakeholders(Model model) {		
		model.addAttribute("stakeholders", data.findAll());
		return "stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/{id}")
	public String handleViewStakeholder(@PathVariable Integer id, Model model) {
		var stakeholder = data.findById(id).get();
		model.addAttribute("stakeholder", stakeholder);
		var requirements = stakeholder.getRequirements();
		model.addAttribute("reqs", requirements);
		return "stakeholder/show";
	}
	
	@GetMapping(path = "/stakeholder/{id}/edit")
	public String handleEditStakeholder(@PathVariable Integer id, Model model) {
		var stakeholder = data.findById(id).get();
		model.addAttribute("stakeholder", stakeholder);
		
		var linkedRequirements = stakeholder.getRequirements();
		model.addAttribute("reqs", linkedRequirements);
		
		var linkable = new ArrayList<Requirement>();
		reqData.findAll().forEach(linkable::add);
		linkable.removeAll(linkedRequirements);
		model.addAttribute("linkable_reqs", linkable);
		
		return "stakeholder/edit"; 
	}
	
	@PutMapping(path = "/stakeholder/{id}")
	public String handleModifyStakeholder(
			@PathVariable Integer id, 
			@RequestParam String stakeholder_name, 
			@RequestParam String stakeholder_description, Model model, RedirectAttributes redirectAttributes) {
		var stakeholder = data.findById(id).get();
		if(stakeholder != null) {
			if(stakeholder_name != null && !stakeholder_name.equals(stakeholder.getName())) {
				stakeholder.setName(stakeholder_name);
				data.save(stakeholder);
				redirectAttributes.addFlashAttribute("messages",List.of("Information updated!"));
			}
			if(stakeholder_description != null && !stakeholder_description.equals(stakeholder.getDescription())) {
				stakeholder.setDescription(stakeholder_description);
				data.save(stakeholder);
				redirectAttributes.addFlashAttribute("messages",List.of("Information updated!"));
			}
		}
		return "redirect:/stakeholder/" + id;
	}
	
	@PostMapping(path = "/stakeholder") 
	public String handleCreateStakeholder(RedirectAttributes redirectAttributes) {
		var stakeholder = new Stakeholder();
		data.save(stakeholder);
		redirectAttributes.addFlashAttribute("messages", List.of("Stakeholder created!"));
		return "redirect:/stakeholder/" + stakeholder.getId() + "/edit";
	}
	

	
}
