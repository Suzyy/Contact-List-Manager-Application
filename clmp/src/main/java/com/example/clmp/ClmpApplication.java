package com.example.clmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClmpApplication.class, args);
	}

}
