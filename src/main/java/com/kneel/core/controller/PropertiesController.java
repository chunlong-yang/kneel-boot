package com.kneel.core.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kneel.core.entity.PlmProperties;
import com.kneel.core.entity.repository.PlmPropertiesRepository;

@RestController
@RequestMapping("/prop")
public class PropertiesController {
	
	@Autowired
	private PlmPropertiesRepository plmPropertiesRepository;
	
	
	@GetMapping("/list")
	public List<PlmProperties> getAllNotes() { 
	    return new ArrayList<PlmProperties>((Collection<PlmProperties>)plmPropertiesRepository.findAll());
	}
	
	@GetMapping("/getByProperty")
	public PlmProperties getByProperty(String property){
		return plmPropertiesRepository.findByProperty(property);
	}

}
