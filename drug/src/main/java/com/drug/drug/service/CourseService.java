// src/main/java/com/drug/drug/service/CourseService.java

package com.drug.drug.service;

import com.drug.drug.entity.Course;
import com.drug.drug.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
