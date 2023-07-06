package com.treeleaf.rating.dao;

import com.treeleaf.rating.dto.Rating;

import java.util.List;

public interface RatingDao {
    void addRating(String userId, String blogId, Rating rating);

    List<Rating> getUserRating(String userId, String blogId);

    List<Rating> getBlogRating(String blogId);
}
