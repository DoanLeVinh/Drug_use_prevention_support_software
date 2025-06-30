package com.drug.prevention.service;

import com.drug.prevention.entity.Course;
import java.util.List;

public interface CourseService {
    Course createCourse(Course course);
    List<Course> getAllCourses();
}
