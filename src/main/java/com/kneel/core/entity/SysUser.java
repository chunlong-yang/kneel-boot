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

@SuppressWarnings("serial")
@Entity
public class SysUser implements Serializable {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sys_userseq")
    @SequenceGenerator(sequenceName = "sys_userseq", allocationSize = 1, name = "sys_userseq")
	private Long id;
	
	private String name;
	 
	private String password;
	
	private String active;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL) 
    @JoinTable( 
        name = "sys_user_role", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Collection<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Collection<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
 
}
