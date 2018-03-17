package com.kneel.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={
		"com.kneel.core.entity.repository"
		,"com.kneel.core.controller"
		,"com.kneel.core.config"
})
@EnableJpaRepositories("com.kneel.core.entity")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
