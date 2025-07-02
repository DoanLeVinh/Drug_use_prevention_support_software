package com.drug.drug.controller;

import com.drug.drug.entity.BlogPost;
import com.drug.drug.service.BlogPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BlogController {

    private final BlogPostService blogPostService;

    public BlogController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/blog")
    public String showBlogPage(Model model) {
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        model.addAttribute("blogPosts", blogPosts);
        return "member/blog";
    }
    
    // Có thể thêm các endpoint khác như:
    /*
    @GetMapping("/blog/{id}")
    public String showBlogDetail(@PathVariable Long id, Model model) {
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        model.addAttribute("blogPost", blogPost);
        return "blog-detail";
    }
    */
}