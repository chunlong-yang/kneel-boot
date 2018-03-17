package com.kneel.core.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.BaseApplicationTest;
import com.kneel.core.entity.PlmProperties;
import com.kneel.core.entity.repository.PlmPropertiesRepository;
 
public class PropertiesTests extends BaseApplicationTest {
	
	@Autowired
	private PlmPropertiesRepository plmPropertiesRepository;
	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testJap(){
		Iterator<PlmProperties> it = plmPropertiesRepository.findAll().iterator(); 
		while(it.hasNext()){
			PlmProperties po = it.next();
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	} 
	
	@Test
	public void testNamedQuery(){
		List<PlmProperties> it = plmPropertiesRepository.findByPropcategory("archive"); 
		for(PlmProperties po:it){ 
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	} 
	
	@Test
	public void testFindByEnv(){
		List<PlmProperties> it = plmPropertiesRepository.findByEnv("DEV1"); 
		for(PlmProperties po:it){ 
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	} 
	
	@Test
	public void testList(){
		List<PlmProperties> it = new ArrayList<PlmProperties>((Collection<PlmProperties>)plmPropertiesRepository.findAll());
		for(PlmProperties po:it){ 
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	}
	
	@Test
	public void testFindByProperty(){
		PlmProperties po = plmPropertiesRepository.findByProperty("maxThreads");
		 
		System.out.println(po.getPropertyid());
		System.out.println(po.getProperty());
		System.out.println(po.getValue()); 
	} 
	
	@Test
	public void testInsert(){
		PlmProperties entity = new PlmProperties();
		entity.setEnv("DEV");
		entity.setOverride("default");
		entity.setPriority(0L);
		entity.setPropcategory("archive");
		entity.setProperty("RetentionMonths");
		entity.setValue("144");
		
		plmPropertiesRepository.save(entity);
	}
}
