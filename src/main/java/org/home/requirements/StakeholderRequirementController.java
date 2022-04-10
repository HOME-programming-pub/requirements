package org.home.requirements;

import org.home.requirements.model.RequirementsModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StakeholderRequirementController {

	private RequirementsModel data = RequirementsModel.getInstance();	
	
	@PostMapping(path = "/stakeholder/{id}/requirement")
	public String handleLinkRequirement(
			@PathVariable Long id, 
			@RequestParam(required = false) String requirement_name, 
			@RequestParam(required = false) Long requirement_id, Model model) {
		var stakeholder = data.getStakeholders().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
		if(stakeholder != null) {
			if(requirement_name != null)
				data.createRequirement(requirement_name);
			if(requirement_id != null)
				data.createLink(requirement_id, stakeholder);
		}
		return "redirect:/stakeholder/" + id;
	}
	
}
