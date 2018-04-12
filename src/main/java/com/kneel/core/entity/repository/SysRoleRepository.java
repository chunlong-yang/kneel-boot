package com.kneel.core.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kneel.core.entity.SysRole;

public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
	 

	SysRole findByName(String name);
}
