package com.kneel.core.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kneel.core.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
 
	SysUser findByName(String name);
}
