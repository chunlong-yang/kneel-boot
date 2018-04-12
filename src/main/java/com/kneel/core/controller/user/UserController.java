package com.kneel.core.controller.user;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kneel.core.entity.SysUser;
import com.kneel.core.entity.repository.SysRoleRepository;
import com.kneel.core.entity.repository.SysUserRepository;

@Controller
public class UserController {
	
	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Autowired
	private EntityManager entitymanager;

	@GetMapping("/user/index") 
	public String getUser(Model model) { 
		System.out.println("Thymeleaf");
		model.addAttribute("thyme", "The Thymeleaf User");
		return "index";
	} 

	@RequestMapping(value = "/user/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout"; 
	}
	
	@RequestMapping(value = "/user/register")
	@Transactional
	public String register(String username,String password,Model model) {
		
		SysUser existUser = sysUserRepository.findByName(username);
		if(existUser == null){
			SysUser user = new SysUser();
			user.setName(username);
			user.setPassword(password);
			user.setActive("Y");
			user.setSysRoles(Arrays.asList(sysRoleRepository.getOne(2L))); 
			entitymanager.persist(user);
			return "forward:/login"; 
		}else{
			model.addAttribute("error", "User "+username+" is exists, please change to others");
			return "register";
		}
		
		
	}
	
}
