package com.treeleaf.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
@EnableEurekaClient
public class BlogApplication {

	private final JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void createBlogTableInDB() {
		jdbcTemplate.update("""
                CREATE TABLE IF NOT EXISTS BLOG (
                    id VARCHAR(50) PRIMARY KEY,
                    title VARCHAR(50),
                    content VARCHAR(255),
                    userId VARCHAR(50),
                    CONSTRAINT fk_user FOREIGN KEY (userId)
                    REFERENCES user (id)
                    on delete cascade
                    on update cascade
                )""");
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
