package com.treeleaf.blog.services;


import com.treeleaf.blog.config.IsUser;
import com.treeleaf.blog.dto.Blog;
import reactor.core.publisher.Flux;

import java.util.List;

public interface BlogService {

    List<Blog> getBlog();

    void addBlog(Blog blog);

    List<Blog> getBlogList();

    void updateBlog(String blogId, Blog blog);

    void deleteBlog(String blogId);

    Flux<Blog> getUserBlogRating();
}
