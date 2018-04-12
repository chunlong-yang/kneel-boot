package com.kneel.core.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.BaseApplicationTest;
import com.kneel.core.entity.SysProperties;
import com.kneel.core.entity.repository.SysPropertiesRepository;
 
public class PropertiesTests extends BaseApplicationTest {
	
	@Autowired
	private SysPropertiesRepository sysPropertiesRepository;
	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testJap(){
		Iterator<SysProperties> it = sysPropertiesRepository.findAll().iterator(); 
		while(it.hasNext()){
			SysProperties po = it.next();
			System.out.println(po.getId());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	} 
	
	@Test
	public void testNamedQuery(){
		List<SysProperties> it = sysPropertiesRepository.findByPropcategory("archive"); 
		for(SysProperties po:it){ 
			System.out.println(po.getId());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	} 
	
	@Test
	public void testFindByEnv(){
		List<SysProperties> it = sysPropertiesRepository.findByEnv("DEV1"); 
		for(SysProperties po:it){ 
			System.out.println(po.getId());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	} 
	
	@Test
	public void testList(){
		List<SysProperties> it = new ArrayList<SysProperties>((Collection<SysProperties>)sysPropertiesRepository.findAll());
		for(SysProperties po:it){ 
			System.out.println(po.getId());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	}
	
	@Test
	public void testFindByProperty(){
		SysProperties po = sysPropertiesRepository.findByProperty("maxThreads");
		 
		System.out.println(po.getId());
		System.out.println(po.getProperty());
		System.out.println(po.getValue()); 
	} 
	
	@Test
	public void testInsert(){
		SysProperties entity = new SysProperties();
		entity.setEnv("DEV");
		entity.setOverride("default");
		entity.setPriority(0L);
		entity.setPropcategory("archive");
		entity.setProperty("RetentionMonths");
		entity.setValue("144");
		
		sysPropertiesRepository.save(entity);
	}
}
