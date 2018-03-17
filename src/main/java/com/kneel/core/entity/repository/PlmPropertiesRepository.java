package com.kneel.core.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kneel.core.entity.PlmProperties;

public interface PlmPropertiesRepository extends JpaRepository<PlmProperties,Long>{
 
	PlmProperties findByProperty(String property);
	 
	List<PlmProperties> findByPropcategory(String propcategory);
 
	List<PlmProperties> findByEnv(String env);
}
