package com.kneel.core.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.BaseApplicationTest;
import com.kneel.core.entity.SysProperties;

public class EntityTests extends BaseApplicationTest {

	@Autowired
	private EntityManager entitymanager;

	/**
	 * QL string
	 */
	@Test
	public void testEntityManager(){
		TypedQuery<SysProperties> query = entitymanager.createQuery("SELECT p FROM SysProperties p WHERE  p.env='DEV1'",SysProperties.class);
		List<SysProperties> list = query.getResultList();
		for(SysProperties po:list){
			System.out.println(po.getId());
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
		SysProperties po = entitymanager.find(SysProperties.class, 1L);
		System.out.println(po.getId());
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
		Query query = entitymanager.createNativeQuery("SELECT p.* FROM Plm_Properties p WHERE  p.env='DEV1'", SysProperties.class);
		List<SysProperties> list = query.getResultList();
		for(SysProperties po:list){
			System.out.println(po.getId());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
		
	}
	
	@Test
	public void testNamedQuery(){
		TypedQuery<SysProperties> query = entitymanager.createNamedQuery("User.findByProperty", SysProperties.class);
		query.setParameter(1, "maxThreads");
		List<SysProperties> list = query.getResultList();
		for(SysProperties po:list){
			System.out.println(po.getId());
			System.out.println(po.getProperty());
			System.out.println(po.getValue());
		}
	}
}
