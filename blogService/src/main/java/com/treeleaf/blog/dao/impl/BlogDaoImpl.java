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
    public void addBlog(Blog blog) {
        jdbcTemplate.update("" +
                        "INSERT INTO blog (id, title, content) VALUES (?, ?, ?)",
                blog.getId(), blog.getTitle(), blog.getContent());
    }

    @Override
    public Blog getBlog(String blogId) {
        Blog blog = jdbcTemplate.queryForObject(
                "SELECT * FROM blog WHERE id = ?", new Object[]{blogId},
                new BeanPropertyRowMapper<>(Blog.class));
        log.debug("Fetched blog with id: {} from database", blog.getId());
        return blog;
    }

    @Override
    public List<Blog> getBlogList() {
        List<Blog> blogList = jdbcTemplate.query("SELECT * FROM blog",
                new BeanPropertyRowMapper<>(Blog.class));
        return blogList;
    }


    @Override
    public void updateBlog(String id, Blog blog) {
        jdbcTemplate.update("" +
                        "UPDATE blog SET title = ?, content = ? WHERE id = ?",
                blog.getTitle(), blog.getContent(), id);
    }


    @Override
    public void deleteBlog(String id) {
        jdbcTemplate.update("DELETE from blog WHERE id = ?", id);
    }

}
