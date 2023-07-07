package com.treeleaf.blog.services.impl;

import com.treeleaf.blog.config.IsAdmin;
import com.treeleaf.blog.config.IsUser;
import com.treeleaf.blog.config.SessionManager;
import com.treeleaf.blog.dao.BlogDao;
import com.treeleaf.blog.dao.feign.RatingService;
import com.treeleaf.blog.dto.Blog;
import com.treeleaf.blog.dto.Rating;
import com.treeleaf.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogServiceImpl implements BlogService {

    private final BlogDao blogDao;

    private final SessionManager sessionManager;

    private final RatingService ratingService;

    private final WebClient.Builder loadBalancedWebClientBuilder;

    @IsUser
    @Override
    public List<Blog> getBlog() {
        String userId = sessionManager.getCurrentUserId();
        return blogDao.getBlog(userId);
    }

    @IsUser
    @Override
    public void addBlog(Blog blog) {
        String userId = sessionManager.getCurrentUserId();
        String blogId = UUID.randomUUID().toString();
        blog.setId(blogId);
        blogDao.addBlog(userId, blog);
    }

    @IsAdmin
    @Override
    public List<Blog> getBlogList() {
        return blogDao.getBlogList();
    }

    @IsUser
    @Override
    public void updateBlog(String blogId, Blog blog) {
        blog.setId(blogId);
        String userId = sessionManager.getCurrentUserId();
        blogDao.updateBlog(userId, blog);
    }

    @IsUser
    @Override
    public void deleteBlog(String blogId) {
        String userId = sessionManager.getCurrentUserId();
        blogDao.deleteBlog(userId, blogId);
    }

    @IsUser
    @Override
    public Flux<Blog> getUserBlogRating() {
        log.info("Enter into getUserBlogRating. BlogServiceImpl");
        String userId = sessionManager.getCurrentUserId();
        String role = sessionManager.getUserRole();
        Map<String, String> headers = Map.of("id", userId, "role", role);

        List<Blog> blogs = blogDao.getBlog(userId);

        return Flux.just(blogs)
                .flatMap(Flux::fromIterable)
                .flatMap(blog -> loadBalancedWebClientBuilder.build()
                        .get().uri("http://RATING-SERVICE/rating/" + blog.getId())
                        .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Rating.class))
                        .flatMap(ratingItem -> Flux.just(new Blog(blog.getId(), blog.getTitle(), blog.getContent(), ratingItem))));
    }

    private Flux<Rating[]> getBlogRating(Blog blog) {
        Flux<Rating[]> ratingFlux = loadBalancedWebClientBuilder.build()
                .get().uri("http://RATING-SERVICE/rating/" + blog.getId())
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Rating[].class));
        return ratingFlux;
    }

//    private Blog updateBlog(Blog blog) {
//        List<Rating> ratings = ratingService.getBlogRating(blog.getId());
//        blog.setRating(ratings);
//        return blog;
//    }
}
