// 📄 entity/Answer.java

package com.drug.prevention.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Question question;

    private String answerText;
    private LocalDateTime answerTime = LocalDateTime.now();

    // Getters và Setters
}
