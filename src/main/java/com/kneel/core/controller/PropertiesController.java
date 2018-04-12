package com.kneel.core.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kneel.core.entity.SysProperties;
import com.kneel.core.entity.repository.SysPropertiesRepository;

@RestController
@RequestMapping("/prop")
public class PropertiesController {
	
	@Autowired
	private SysPropertiesRepository sysPropertiesRepository;
	
	
	@GetMapping("/list")
	public List<SysProperties> getAllNotes() { 
	    return new ArrayList<SysProperties>((Collection<SysProperties>)sysPropertiesRepository.findAll());
	}
	
	@GetMapping("/getByProperty")
	public SysProperties getByProperty(String property){
		return sysPropertiesRepository.findByProperty(property);
	}

}
