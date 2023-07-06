package com.treeleaf.rating.controllers;


import com.treeleaf.rating.dto.Rating;
import com.treeleaf.rating.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{blogId}")
    public void addRating(@PathVariable String blogId, @RequestBody Rating rating) {
        ratingService.addRating(blogId, rating);
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
