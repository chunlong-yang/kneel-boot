package com.kneel.core.repository;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.BaseApplicationTest;
import com.kneel.core.entity.SysRole;
import com.kneel.core.entity.SysUser;
import com.kneel.core.entity.repository.SysRoleRepository;
import com.kneel.core.entity.repository.SysUserRepository;

public class SysUserRepositoryTest extends BaseApplicationTest {

	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Autowired
	private EntityManager entitymanager;
	
	/**
	 * lazy load, must be in transaction.
	 */
	@Test
	@Transactional
	public void testFind(){
		SysUser user = sysUserRepository.findByName("admin");
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getActive());
		
		Collection<SysRole> roles = user.getSysRoles();
		for(SysRole role:roles){
			System.out.println(role.getId());
			System.out.println(role.getName());
		}
	}
	
	/**
	 * load earlier, don't need transaction.
	 */
	@Test
	public void testFindEAGER(){
		SysUser user = sysUserRepository.findByName("admin");
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getActive());
		
		Collection<SysRole> roles = user.getSysRoles();
		for(SysRole role:roles){
			System.out.println(role.getId());
			System.out.println(role.getName());
		}
	}
	@Test
	public void testDelete(){
		SysUser user = sysUserRepository.findOne(34L); 
		sysUserRepository.delete(user);
	}
	
	@Test
	public void testAddCascade(){
		SysUser user = sysUserRepository.findByName("admin");
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getActive());
		
		SysRole rolet = new SysRole();
		rolet.setName("AddRoleTest");
		
		Collection<SysRole> roles = user.getSysRoles();
		roles.add(rolet);
		for(SysRole role:roles){
			System.out.println(role.getId());
			System.out.println(role.getName());
		}
		
		sysUserRepository.save(user);
	}
	
	@Test
	@Transactional
	public void testUpdateCascade(){
		
		SysUser user = new SysUser();
		user.setName("abc");
		user.setPassword("abc");
		user.setActive("Y");
		SysRole sysRole = sysRoleRepository.getOne(2L);
		user.setSysRoles(Arrays.asList(sysRole));//add normal user role.
		//sysUserRepository.save(user); 
		entitymanager.persist(user); 
		entitymanager.flush();
		entitymanager.close();
		
	}
	
	
}
