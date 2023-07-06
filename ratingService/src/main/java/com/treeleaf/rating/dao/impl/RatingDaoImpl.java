package com.treeleaf.rating.dao.impl;

import com.treeleaf.rating.dao.RatingDao;
import com.treeleaf.rating.dto.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RatingDaoImpl implements RatingDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addRating(String userId, String blogId, Rating rating) {
        jdbcTemplate.update("" +
                        "INSERT INTO rating (id, rating, comment, blogId, userId) " +
                        "VALUES (?, ?, ?, ?, ?)",
                rating.getId(), rating.getRating(), rating.getComment(), blogId, userId);
    }


    @Override
    public List<Rating> getUserRating(String userId, String blogId) {
        List<Rating> ratings = jdbcTemplate.query(
                "SELECT * FROM rating WHERE userId = ? and blogId = ?",
                new Object[]{userId, blogId},
                new BeanPropertyRowMapper<>(Rating.class));
        return ratings;
    }

    @Override
    public List<Rating> getBlogRating(String blogId) {
        List<Rating> ratings = jdbcTemplate.query(
                "SELECT * FROM rating WHERE blogId = ?",
                new Object[]{blogId},
                new BeanPropertyRowMapper<>(Rating.class));
        return ratings;
    }
}
