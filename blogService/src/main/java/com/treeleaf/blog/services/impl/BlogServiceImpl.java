package com.treeleaf.blog.services.impl;

import com.treeleaf.blog.config.IsAdmin;
import com.treeleaf.blog.config.IsUser;
import com.treeleaf.blog.config.SessionManager;
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

    private final SessionManager sessionManager;

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
}
