package com.kneel.core;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages="com.kneel.core.entity.repository")
@EnableJpaRepositories("com.kneel.core.entity")
public class ApplicationTest {

	private static final Logger log = Logger.getLogger(ApplicationTest.class);

	public static void main(String[] args) {
		log.info("Application Test : running!");
		SpringApplication.run(ApplicationTest.class);
	}

}
