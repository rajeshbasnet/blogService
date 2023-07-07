package com.treeleaf.rating.controllers;


import com.treeleaf.rating.dto.Rating;
import com.treeleaf.rating.services.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Rating.class);

    private final RatingService ratingService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/{blogId}")
    public ResponseEntity<Map<String, String>> addRating(@PathVariable String blogId, @RequestBody Rating rating) {
        LOGGER.info("Request for addRating. Port: {}", port);
        ratingService.addRating(blogId, rating);
        return ResponseEntity.ok(Map.of("message", "Rating added successfully"));
    }

    @GetMapping("/user/{blogId}")
    public ResponseEntity<List<Rating>> getUserRating(@PathVariable String blogId) {
        LOGGER.info("Request for getUserRating. Port: {}", port);
        return ResponseEntity.ok(ratingService.getUserRating(blogId));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<List<Rating>> getBlogRating(@PathVariable String blogId) {
        LOGGER.info("Request for getBlogRating. Port: {}", port);
        return ResponseEntity.ok(ratingService.getBlogRating(blogId));
    }
}
