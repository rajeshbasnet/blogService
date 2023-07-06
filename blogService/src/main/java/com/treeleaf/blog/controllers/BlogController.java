package com.treeleaf.blog.controllers;


import com.treeleaf.blog.Util.Util;
import com.treeleaf.blog.dto.Blog;
import com.treeleaf.blog.dto.BlogResponse;
import com.treeleaf.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponse> addBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
        return ResponseEntity.ok(new BlogResponse(Util.BLOG_ADDED));
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getBlog() {
        return ResponseEntity.ok(blogService.getBlog());
    }

    @GetMapping("/all")
    public List<Blog> getBlogList() {
        return blogService.getBlogList();
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogResponse> updateBlog(@PathVariable String blogId, @RequestBody Blog blog) {
        blogService.updateBlog(blogId, blog);
        return ResponseEntity.ok(new BlogResponse(Util.BLOG_UPDATED));
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<BlogResponse> deleteBlog(@PathVariable String blogId) {
        blogService.deleteBlog(blogId);
        return ResponseEntity.ok(new BlogResponse(Util.BLOG_DELETED));
    }

    @GetMapping("/rating")
    public List<Blog> getUserBlogRating() {
        return blogService.getUserBlogRating();
    }

}
