package com.kneel.core.gen;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kneel.core.BaseApplicationTest;
import com.kneel.core.gen.config.support.DefaultBuildFactory;

public class DefaultBuildFactoryTest extends BaseApplicationTest {

	@Autowired
	private DefaultBuildFactory factory;
	
	@Test
	public void testCSVSQL(){
		String csvFile = "plm_rpt_columnconfig.csv";
		factory.buildCSVSql("C:\\eclipse\\workspace\\spring-boot\\kneel-boot", "src\\test\\resources", csvFile); 
	}
}
