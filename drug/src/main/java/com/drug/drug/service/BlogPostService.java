package com.drug.drug.service;

import com.drug.drug.entity.BlogPost;
import com.drug.drug.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public BlogPost getPostById(Long id) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        return post.orElse(null);
    }

    public List<BlogPost> getPostsByCategory(String category) {
        return blogPostRepository.findByCategory(category);
    }

    public List<BlogPost> getPostsByTag(String tag) {
        return blogPostRepository.findByAudience(tag);
    }

    public List<BlogPost> searchPosts(String keyword) {
        return blogPostRepository.searchPosts(keyword);
    }
}