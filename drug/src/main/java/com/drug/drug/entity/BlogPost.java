package com.drug.drug.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog_post")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String summary;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String author;

    private LocalDateTime createdAt;
    private String category;
    private String audience;
    private String imageUrl;

    // Constructors
    public BlogPost() {}

    public BlogPost(String title, String summary, String content, String author,
                    LocalDateTime createdAt, String category, String audience, String imageUrl) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.category = category;
        this.audience = audience;
        this.imageUrl = imageUrl;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAudience() { return audience; }
    public void setAudience(String audience) { this.audience = audience; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
