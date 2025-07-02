package com.drug.drug.repository;

import com.drug.drug.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    
    // Custom query để lấy bài viết theo category
    List<BlogPost> findByCategory(String category);
    
    // Custom query để lấy bài viết theo audience (tag)
    List<BlogPost> findByAudience(String audience);
    
    // Custom query để tìm kiếm bài viết
    @Query("SELECT p FROM BlogPost p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.summary) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<BlogPost> searchPosts(String keyword);
}