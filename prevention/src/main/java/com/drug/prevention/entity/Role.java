package com.drug.prevention.entity;

import jakarta.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleEnum name;

    public Role() {}
    public Role(RoleEnum name) { this.name = name; }

    // getters & setters
    public Long getId() { return id; }
    public RoleEnum getName() { return name; }
    public void setName(RoleEnum name) { this.name = name; }
}
