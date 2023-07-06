package com.treeleaf.rating.services.impl;

import com.treeleaf.rating.config.SessionManager;
import com.treeleaf.rating.dao.RatingDao;
import com.treeleaf.rating.dto.Rating;
import com.treeleaf.rating.services.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RatingServiceImpl implements RatingService {

    private final RatingDao ratingDao;

    private final SessionManager sessionManager;

    @Override
    public void addRating(String blogId, Rating rating) {
        String userId = sessionManager.getCurrentUserId();
        ratingDao.addRating(userId, blogId, rating);
    }

    @Override
    public List<Rating> getUserRating(String blogId) {
        String userId = sessionManager.getCurrentUserId();
        return ratingDao.getUserRating(userId, blogId);
    }

    @Override
    public List<Rating> getBlogRating(String blogId) {
        return ratingDao.getBlogRating(blogId);
    }

}
