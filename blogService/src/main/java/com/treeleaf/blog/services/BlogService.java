package com.treeleaf.blog.services;


import com.treeleaf.blog.dto.Blog;

import java.util.List;

public interface BlogService {
    public void addBlog(Blog blog);

    public Blog getBlog(String blogId);

    public List<Blog> getBlogList();

    void updateBlog(String id, Blog blog);

    void deleteBlog(String id);
}
