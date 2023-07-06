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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogDao blogDao;

    private final SessionManager sessionManager;

    private final RatingService ratingService;

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
    public List<Blog> getUserBlogRating() {
        String userId = sessionManager.getCurrentUserId();
        String role = sessionManager.getUserRole();
        Map<String, String> headers = Map.of("id", userId, "role", role);

        List<Blog> blogs = blogDao.getBlog(userId);

        return blogs.parallelStream()
                .map(this::updateBlog)
                .collect(Collectors.toList());
    }

    private Blog updateBlog(Blog blog) {
        List<Rating> ratings = ratingService.getBlogRating(blog.getId());
        blog.setRating(ratings);
        return blog;
    }
}
