package org.home.requirements;


import org.home.requirements.model.Requirement;
import org.home.requirements.model.RequirementRepository;
import org.home.requirements.model.Stakeholder;
import org.home.requirements.model.StakeholderRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RequirementsApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(RequirementsApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(StakeholderRepository data, RequirementRepository reqData) {

		return args -> {
					data.save(new Stakeholder("Driver", "The driver of the Fantasy car."));
					data.save(new Stakeholder("Owner", "The owner/buyer of the Fantasy car."));
					data.save(new Stakeholder("Mechanic", "Mechanical engineer who maintains a car."));
					data.save(new Stakeholder("Upper management", "The management of our business."));
					data.save(new Stakeholder("Legal department", "The legal department of Fantasy Automotive."));
					
					reqData.save(new Requirement("Operability at highway speeds.", "The system shall be operable at normal highway speeds."));
					reqData.save(new Requirement("Conformance to ISO 26262.", "The driving function shall conform to the safety rules in ISO 26262."));
		};
	}

}
