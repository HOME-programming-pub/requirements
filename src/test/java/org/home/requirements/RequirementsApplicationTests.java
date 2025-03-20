package org.home.requirements;

import org.home.requirements.model.RequirementRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.home.requirements.model.Requirement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequirementsApplicationTests {

	@Autowired private RequirementRepository data;

	@Test
	void contextLoads() {
		assertNotNull(data); // check if repo has been injected
		assertEquals(2, data.count());
		var contents = data.findAll(); // getting auto-initialized data from database
		contents.forEach(RequirementsApplicationTests::printRequirementData); // print each requirement
	}

	private static void printRequirementData(Requirement req) {
		System.out.println("Name: " + req.getName());
		System.out.println("Description: " + req.getDescription());
		System.out.println("ID: " + req.getId());
	}

}
