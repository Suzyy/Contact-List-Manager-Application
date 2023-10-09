package com.example.clmp;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
