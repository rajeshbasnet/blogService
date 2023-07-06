package com.treeleaf.blog.services.impl;

import com.treeleaf.blog.config.IsAdmin;
import com.treeleaf.blog.config.IsUser;
import com.treeleaf.blog.dao.BlogDao;
import com.treeleaf.blog.dto.Blog;
import com.treeleaf.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogDao blogDao;

    @IsUser
    @Override
    public Blog getBlog(String blogId) {
        return blogDao.getBlog(blogId);
    }

    @IsUser
    @Override
    public void addBlog(Blog blog) {
        String blogId = UUID.randomUUID().toString();
        blog.setId(blogId);
        blogDao.addBlog(blog);
    }

    @IsAdmin
    @Override
    public List<Blog> getBlogList() {
        return blogDao.getBlogList();
    }

    @IsUser
    @Override
    public void updateBlog(String id, Blog blog) {
        blogDao.updateBlog(id, blog);
    }

    @IsUser
    @Override
    public void deleteBlog(String id) {
        blogDao.deleteBlog(id);
    }
}
