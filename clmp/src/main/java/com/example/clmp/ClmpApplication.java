package com.example.clmp;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.clmp.entity.User;
import com.example.clmp.repo.UserRepo;

@EnableWebMvc
@SpringBootApplication
@EnableJpaAuditing
public class ClmpApplication {

	@Autowired
	private UserRepo userRepo;

	@PostConstruct
	public void initUser() {
		List<User> users = Stream.of(
			new User(101, "user1", "password1", "ROLE_ADMIN"),
			new User(102, "user2", "password2", "ROLE_USER")
		).collect(Collectors.toList());
		userRepo.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(ClmpApplication.class, args);
	}

}
