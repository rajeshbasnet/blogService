package com.treeleaf.blog.services;


import com.treeleaf.blog.dto.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> getBlog();

    void addBlog(Blog blog);

    List<Blog> getBlogList();

    void updateBlog(String blogId, Blog blog);

    void deleteBlog(String blogId);

    List<Blog> getUserBlogRating();
}
