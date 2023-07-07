package com.treeleaf.blog.dao.feign;

import com.treeleaf.blog.dto.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @GetMapping("/rating/{blogId}")
    public List<Rating> getBlogRating(@PathVariable String blogId);
}
