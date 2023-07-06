package com.treeleaf.blog.dao.feign;

import com.treeleaf.blog.dto.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "RATING-SERVICE", url = "http://localhost:8083")
public interface RatingService {

    @GetMapping("/rating/{blogId}")
    public List<Rating> getBlogRating(@PathVariable String blogId);
}
