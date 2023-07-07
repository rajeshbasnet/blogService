package com.treeleaf.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@EnableEurekaClient
@SpringBootApplication
public class RatingApplication {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createRatingTableInDb() {
        jdbcTemplate.update("""
                CREATE TABLE IF NOT EXISTS RATING (
                    id VARCHAR(50) PRIMARY KEY,
                    comment VARCHAR(50),
                    rating VARCHAR(255),
                    userId VARCHAR(50),
                    blogId VARCHAR(50),
                    CONSTRAINT fk_blog_rating FOREIGN KEY (blogId)
                    REFERENCES blog(id)
                    on delete cascade
                    on update cascade,
                    CONSTRAINT fk_user_rating FOREIGN KEY (userId)
                    REFERENCES user (id)
                    on delete cascade
                    on update cascade
                )""");
    }

    public static void main(String[] args) {
        SpringApplication.run(RatingApplication.class, args);
    }

}
