// 📄 entity/Survey.java

package com.drug.prevention.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    // Getters và Setters
}
