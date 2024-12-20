package com.evehunt.evehuntjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@EnableScheduling
@EnableWebMvc
@SpringBootApplication
public class EveHuntJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveHuntJavaApplication.class, args);
	}

}
