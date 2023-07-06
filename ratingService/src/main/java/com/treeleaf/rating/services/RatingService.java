package com.treeleaf.rating.services;

import com.treeleaf.rating.dto.Rating;

import java.util.List;

public interface RatingService {
    void addRating(String blogId, Rating rating);

    List<Rating> getUserRating(String blogId);

    List<Rating> getBlogRating(String blogId);
}
