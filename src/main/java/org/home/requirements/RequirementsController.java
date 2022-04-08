package org.home.requirements;

import java.util.ArrayList;
import java.util.List;

import org.home.requirements.model.Stakeholder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequirementsController {
	
	private static List<Stakeholder> stakeholders;
	
	public RequirementsController() {
		stakeholders = new ArrayList<Stakeholder>();
		stakeholders.add(new Stakeholder("Driver", "The driver of the Fantasy car.", 1000));
		stakeholders.add(new Stakeholder("Owner", "The owner/buyer of the Fantasy car.", 1001));
		stakeholders.add(new Stakeholder("Mechanic", "Mechanical engineer who maintains a car.", 1002));
		stakeholders.add(new Stakeholder("Upper management", "The management of our business.", 1003));
		stakeholders.add(new Stakeholder("Legal department", "The legal department of Fantasy Automotive.", 1004));
	}
	
	
	@GetMapping(path = "/stakeholder/list")
	public String listStakeholders(Model model) {		
		model.addAttribute("stakeholders", stakeholders);
		return "stakeholder/list";
	}
	
}
