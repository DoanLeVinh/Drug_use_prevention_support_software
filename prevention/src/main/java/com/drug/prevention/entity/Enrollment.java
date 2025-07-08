// 📄 entity/Enrollment.java

package com.drug.prevention.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "course_id"})
})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

    private LocalDateTime enrollDate = LocalDateTime.now();

    // Getters và Setters
}
