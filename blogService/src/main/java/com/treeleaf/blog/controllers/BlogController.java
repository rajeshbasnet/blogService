package com.treeleaf.blog.controllers;


import com.treeleaf.blog.dto.Blog;
import com.treeleaf.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public void addBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
    }

    @GetMapping("/{blogId}")
    public Blog getBlog(@PathVariable String blogId) {
        return blogService.getBlog(blogId);
    }

    @GetMapping
    public List<Blog> getBlogList() {
        return blogService.getBlogList();
    }

    @PutMapping("/{blogId}")
    public void updateBlog(@PathVariable String blogId, @RequestBody Blog blog) {
        blogService.updateBlog(blogId, blog);
    }

    @DeleteMapping("/{blogId}")
    public void deleteBlog(@PathVariable String blogId) {
        blogService.deleteBlog(blogId);
    }

}
