// 📄 entity/Consultant.java

package com.drug.prevention.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String degree;
    private String specialty;

    @ElementCollection
    private List<String> availableSlots; // ví dụ: ["2025-07-10T14:00", "2025-07-11T09:30"]

    // Getters và Setters
}
