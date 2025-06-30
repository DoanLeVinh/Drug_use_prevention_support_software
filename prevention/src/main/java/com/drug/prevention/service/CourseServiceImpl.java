package com.drug.prevention.service;

import com.drug.prevention.entity.Course;
import com.drug.prevention.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Course createCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return repository.findAll();
    }
}
