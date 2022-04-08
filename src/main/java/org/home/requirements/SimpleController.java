package org.home.requirements;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {

	@GetMapping(path = "/")
	@ResponseBody
	public String hello() {
		return "Hallo Welt!";
	}
	
}
