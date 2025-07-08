// 📄 controller/EnrollmentController.java

package com.drug.prevention.controller;

import com.drug.prevention.entity.Enrollment;
import com.drug.prevention.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // Đăng ký khóa học
    @PostMapping("/register")
    public String register(@RequestParam Long userId, @RequestParam Long courseId) {
        return enrollmentService.enrollUser(userId, courseId);
    }

    // Lấy danh sách khóa học đã đăng ký của 1 user
    @GetMapping("/user/{userId}")
    public List<Enrollment> getByUser(@PathVariable Long userId) {
        return enrollmentService.getEnrollmentsByUser(userId);
    }

    // Lấy danh sách người dùng đã đăng ký 1 khóa học
    @GetMapping("/course/{courseId}")
    public List<Enrollment> getByCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }
}

