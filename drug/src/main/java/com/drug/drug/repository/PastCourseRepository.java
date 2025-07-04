package com.drug.drug.repository;

import com.drug.drug.entity.PastCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PastCourseRepository extends JpaRepository<PastCourse, Long> {
    List<PastCourse> findByUserIdOrderByAccessedAtDesc(Long userId);
}
