package com.kneel.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kneel.core.entity.PlmProperties;
import com.kneel.core.entity.repository.PlmPropertiesRepository;

@Controller
public class DefaultController {
	
	@Autowired
	private PlmPropertiesRepository plmPropertiesRepository;
	
	@GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }
 
    @GetMapping("/about/page/{pageNumber}")
    public String about(@PathVariable("pageNumber") Integer pageNumber,Model model) {
    	
    	Page<PlmProperties> currentResults = plmPropertiesRepository.findAll(new PageRequest(pageNumber-1,20));

        model.addAttribute("currentResults", currentResults);

        //Pagination variables
        int startIndex = Math.max(1, pageNumber - 5);
        int endIndex = Math.min(startIndex + 10, currentResults.getTotalPages());

        model.addAttribute("url", "home");
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("endIndex", endIndex);
        model.addAttribute("currentIndex", pageNumber);
        //model.addAttribute("users", users);
 

        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
    
    @GetMapping("/login1")
    public String login1() {
        return "/login1";
    }
	
	
	@GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
