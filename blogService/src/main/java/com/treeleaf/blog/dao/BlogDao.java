package com.treeleaf.blog.dao;

import com.treeleaf.blog.dto.Blog;

import java.util.List;

public interface BlogDao {

    void addBlog(String userId, Blog blog);

    List<Blog> getBlog(String userId);

    List<Blog> getBlogList();

    void updateBlog(String userId, Blog blog);

    void deleteBlog(String userId, String blogId);
}
