package org.home.requirements;

import org.home.requirements.model.Requirement;
import org.home.requirements.model.RequirementRepository;
import org.home.requirements.model.StakeholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StakeholderRequirementController {

	@Autowired private StakeholderRepository data;	
	@Autowired private RequirementRepository reqData;
	
	@PostMapping(path = "/stakeholder/{id}/requirement")
	public String handleLinkRequirement(
			@PathVariable Integer id, 
			@RequestParam(required = false) String requirement_name, 
			@RequestParam(required = false) Integer requirement_id, Model model) {
		var stakeholder = data.findById(id).get();
		if(stakeholder != null) {
			Requirement requirement = null;
			if(requirement_name != null) {
				requirement = new Requirement(requirement_name, null);
				reqData.save(requirement);
			}
			else if(requirement_id != null) {
				requirement = reqData.findById(requirement_id).get();
			}
			stakeholder.addRequirement(requirement);
			data.save(stakeholder);
		}
		return "redirect:/stakeholder/" + id;
	}
	
}
