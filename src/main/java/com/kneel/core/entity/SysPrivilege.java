package com.kneel.core.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@Entity
public class SysPrivilege implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_privilegeseq")
    @SequenceGenerator(sequenceName = "sys_privilegeseq", allocationSize = 1, name = "sys_privilegeseq")
	private Long id;
	
	private String name;
	
	@ManyToMany(mappedBy = "sysPrivileges")
    private Collection<SysRole> sysRoles;


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

	public Collection<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Collection<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	} 
}
