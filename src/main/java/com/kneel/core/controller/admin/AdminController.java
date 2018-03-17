package com.kneel.core.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/admin/index") 
	public String geAdmin(Model model) { 
		System.out.println("Thymeleaf");
		model.addAttribute("thyme", "The Thymeleaf Admin");
		return "index";
	}
}
