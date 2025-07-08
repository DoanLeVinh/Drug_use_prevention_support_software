// 📄 entity/Question.java

package com.drug.prevention.entity;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    // Getters và Setters
}
