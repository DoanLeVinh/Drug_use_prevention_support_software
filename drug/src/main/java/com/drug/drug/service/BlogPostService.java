package com.drug.drug.service;

import com.drug.drug.entity.BlogPost;
import com.drug.drug.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }
}
