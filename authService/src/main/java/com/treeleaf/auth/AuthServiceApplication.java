package com.treeleaf.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthServiceApplication {

	private final JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void createUserTable() {
		jdbcTemplate.update("""
                CREATE TABLE IF NOT EXISTS USER (
                    id VARCHAR(50) PRIMARY KEY,
                    username VARCHAR(50),
                    email VARCHAR(50),
                    password VARCHAR(255),
                    role VARCHAR(10)
                )""");
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
