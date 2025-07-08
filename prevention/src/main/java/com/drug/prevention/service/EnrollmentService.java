// 📄 service/EnrollmentService.java

package com.drug.prevention.service;

import com.drug.prevention.entity.*;
import com.drug.prevention.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;

    public EnrollmentService(EnrollmentRepository enrollmentRepo,
                             UserRepository userRepo,
                             CourseRepository courseRepo) {
        this.enrollmentRepo = enrollmentRepo;
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
    }

    public String enrollUser(Long userId, Long courseId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Course course = courseRepo.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        Optional<Enrollment> existing = enrollmentRepo.findByUserAndCourse(user, course);
        if (existing.isPresent()) {
            return "Người dùng đã đăng ký khóa học này!";
        }

        Enrollment e = new Enrollment();
        e.setUser(user);
        e.setCourse(course);
        enrollmentRepo.save(e);

        return "Đăng ký thành công!";
    }

    public List<Enrollment> getEnrollmentsByUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return enrollmentRepo.findByUser(user);
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow();
        return enrollmentRepo.findByCourse(course);
    }
}
