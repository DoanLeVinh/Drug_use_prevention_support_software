package com.drug.prevention.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String ageGroup; // học sinh, sinh viên, phụ huynh
    private String level;    // nhận thức, kỹ năng từ chối...
}
