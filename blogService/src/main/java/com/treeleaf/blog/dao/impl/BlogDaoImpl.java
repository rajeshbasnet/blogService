package com.treeleaf.blog.dao.impl;

import com.treeleaf.blog.dao.BlogDao;
import com.treeleaf.blog.dto.Blog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BlogDaoImpl implements BlogDao {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void addBlog(String userId, Blog blog) {
        jdbcTemplate.update("" +
                        "INSERT INTO blog (id, title, content, userId) VALUES (?, ?, ?, ?)",
                blog.getId(), blog.getTitle(), blog.getContent(), userId);
    }

    @Override
    public List<Blog> getBlog(String userId) {
        List<Blog> blog = jdbcTemplate.query(
                "SELECT * FROM blog WHERE userId = ?", new Object[]{userId},
                new BeanPropertyRowMapper<>(Blog.class));
        return blog;
    }


    @Override
    public List<Blog> getBlogList() {
        List<Blog> blogList = jdbcTemplate.query("SELECT * FROM blog",
                new BeanPropertyRowMapper<>(Blog.class));
        return blogList;
    }


    @Override
    public void updateBlog(String userId, Blog blog) {
        jdbcTemplate.update("" +
                        "UPDATE blog SET title = ?, content = ? WHERE id = ? AND userId = ?",
                blog.getTitle(), blog.getContent(), blog.getId(), userId);
    }


    @Override
    public void deleteBlog(String userId, String blogId) {
        jdbcTemplate.update("DELETE from blog WHERE id = ? AND userId = ?", blogId, userId);
    }

}
