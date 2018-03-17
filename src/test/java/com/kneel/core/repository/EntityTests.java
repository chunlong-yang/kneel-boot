package com.kneel.core.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.BaseApplicationTest;
import com.kneel.core.entity.PlmProperties;

public class EntityTests extends BaseApplicationTest {

	@Autowired
	private EntityManager entitymanager;

	/**
	 * QL string
	 */
	@Test
	public void testEntityManager(){
		TypedQuery<PlmProperties> query = entitymanager.createQuery("SELECT p FROM PlmProperties p WHERE  p.env='DEV1'",PlmProperties.class);
		List<PlmProperties> list = query.getResultList();
		for(PlmProperties po:list){
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
		
	}
	
	/**
	 * find class and key
	 * 
	 */
	@Test
	public void testEntityFind(){
		PlmProperties po = entitymanager.find(PlmProperties.class, 1L);
		System.out.println(po.getPropertyid());
		System.out.println(po.getProperty());
		System.out.println(po.getValue());
	}
	
	/**
	 * use native query sql.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSQLManager(){
		Query query = entitymanager.createNativeQuery("SELECT p.* FROM Plm_Properties p WHERE  p.env='DEV1'", PlmProperties.class);
		List<PlmProperties> list = query.getResultList();
		for(PlmProperties po:list){
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
		
	}
	
	@Test
	public void testNamedQuery(){
		TypedQuery<PlmProperties> query = entitymanager.createNamedQuery("User.findByProperty", PlmProperties.class);
		query.setParameter(1, "maxThreads");
		List<PlmProperties> list = query.getResultList();
		for(PlmProperties po:list){
			System.out.println(po.getPropertyid());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	}
}
