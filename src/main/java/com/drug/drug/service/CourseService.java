// src/main/java/com/drug/drug/service/CourseService.java

package com.drug.drug.service;

import com.drug.drug.entity.Course;
import com.drug.drug.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    // Lấy tất cả khóa học
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Lấy chi tiết một khóa học theo id
    public Course getCourseById(Long id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        return courseOpt.orElse(null); // Trả về null nếu không tìm thấy
    }
}
