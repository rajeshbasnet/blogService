package com.treeleaf.rating.controllers;


import com.treeleaf.rating.dto.Rating;
import com.treeleaf.rating.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{blogId}")
    public ResponseEntity<Map<String, String>> addRating(@PathVariable String blogId, @RequestBody Rating rating) {
        ratingService.addRating(blogId, rating);
        return ResponseEntity.ok(Map.of("message", "Rating added successfully"));
    }

    @GetMapping("/user/{blogId}")
    public ResponseEntity<List<Rating>> getUserRating(@PathVariable String blogId) {
        return ResponseEntity.ok(ratingService.getUserRating(blogId));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<List<Rating>> getBlogRating(@PathVariable String blogId) {
        return ResponseEntity.ok(ratingService.getBlogRating(blogId));
    }
}
