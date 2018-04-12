package com.kneel.core.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kneel.core.entity.SysProperties;

public interface SysPropertiesRepository extends JpaRepository<SysProperties,Long>{
  
	SysProperties findByProperty(String property);
	 
	List<SysProperties> findByPropcategory(String propcategory);
 
	List<SysProperties> findByEnv(String env);
}
