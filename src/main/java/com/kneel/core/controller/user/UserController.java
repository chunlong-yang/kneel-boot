package com.kneel.core.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/user/index") 
	public String getUser(Model model) { 
		System.out.println("Thymeleaf");
		model.addAttribute("thyme", "The Thymeleaf User");
		return "index";
	}
}
