package com.drug.drug.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Tùy chỉnh package, import, và return view cho đúng dự án của bạn nhé!

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Trang dashboard
    @GetMapping({"", "/", "/dashboard"})
    public String adminDashboard() {
        return "admin/dashboard";
    }

    // Trang quản lý user
    @GetMapping("/users")
    public String adminUsers() {
        return "admin/users";
    }

    // Trang quản lý bác sĩ
    @GetMapping("/doctors")
    public String adminDoctors() {
        return "admin/doctors";
    }

    // Trang quản lý nhân viên
    @GetMapping("/staffs")
    public String adminStaffs() {
        return "admin/staffs";
    }

    // Trang thông báo
    @GetMapping("/notifications")
    public String adminNotifications() {
        return "admin/notifications";
    }

    // Trang thống kê/biểu đồ
    @GetMapping("/stats")
    public String adminStats() {
        return "admin/stats";
    }

    // Trang cá nhân admin
    @GetMapping("/profile")
    public String adminProfile() {
        return "admin/profile";
    }

    @GetMapping("/courses")
    public String adminCourses() {
        return "admin/courses";
    }

    @GetMapping("/statistics")
    public String adminStatistics() {
        return "admin/statistics";
    }

}
