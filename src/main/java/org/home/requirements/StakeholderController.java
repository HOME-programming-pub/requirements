package org.home.requirements;

import java.util.ArrayList;
import java.util.List;

import org.home.requirements.model.Requirement;
import org.home.requirements.model.RequirementsModel;
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
	
	private RequirementsModel data = RequirementsModel.getInstance();	
	
	@GetMapping(path = "/")
	public String handleRoot() {
		return "redirect:stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/list")
	public String handleListStakeholders(Model model) {		
		model.addAttribute("stakeholders", data.getStakeholders());
		return "stakeholder/list";
	}
	
	@GetMapping(path = "/stakeholder/{id}")
	public String handleViewStakeholder(@PathVariable Long id, Model model) {
		var stakeholder = data.getStakeholders().stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("stakeholder", stakeholder);
		var requirements = data.getLinks().stream()
							.filter(pair -> pair.getFirst() == stakeholder)
							.map(pair -> pair.getSecond());
		model.addAttribute("reqs", requirements.toList());
		return "stakeholder/show";
	}
	
	@GetMapping(path = "/stakeholder/{id}/edit")
	public String handleEditStakeholder(@PathVariable Long id, Model model) {
		var stakeholder = data.getStakeholders().stream().filter(x -> x.getId() == id).findFirst().get();
		model.addAttribute("stakeholder", stakeholder);
		var linkedRequirements = data.getLinks().stream()
							.filter(pair -> pair.getFirst() == stakeholder)
							.map(pair -> pair.getSecond())
							.toList();
		model.addAttribute("reqs", linkedRequirements);
		var linkable = new ArrayList<Requirement>();
		linkable.addAll(data.getRequirements());
		linkable.removeAll(linkedRequirements);

		model.addAttribute("linkable_reqs", linkable);
		return "stakeholder/edit"; 
	}
	
	@PutMapping(path = "/stakeholder/{id}")
	public String handleModifyStakeholder(
			@PathVariable Long id, 
			@RequestParam String stakeholder_name, 
			@RequestParam String stakeholder_description, Model model, RedirectAttributes redirectAttributes) {
		var stakeholder = data.getStakeholders().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
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
		var stakeholder = data.createStakeholder();
		if(stakeholder != null)
		{
		    redirectAttributes.addFlashAttribute("messages",List.of("Stakeholder created!"));
		    return "redirect:/stakeholder/" + stakeholder.getId() + "/edit";
		}
		redirectAttributes.addFlashAttribute("messages", List.of("Failed to create new stakeholder!"));
		return "redirect:/";
	}
	

	
}
