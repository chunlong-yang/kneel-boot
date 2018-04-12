package com.kneel.core.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;

@SuppressWarnings("serial")
@Entity
public class SysRole implements Serializable { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_roleseq")
	@SequenceGenerator(sequenceName = "sys_roleseq", allocationSize = 1, name = "sys_roleseq")
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "sysRoles")
	private Collection<SysUser> sysUsers;

	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinTable(name = "sys_role_privilege",
			   joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), 
			   inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
	private Collection<SysPrivilege> sysPrivileges;
	
	public SysRole(){
		
	}
	public SysRole(String name){
		this.name = name;
	}
	public SysRole(Long id,String name){
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public Collection<SysUser> getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(Collection<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	public Collection<SysPrivilege> getSysPrivileges() {
		return sysPrivileges;
	}

	public void setSysPrivileges(Collection<SysPrivilege> sysPrivileges) {
		this.sysPrivileges = sysPrivileges;
	} 
	 
}
