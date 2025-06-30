package com.drug.drug.controller;

import com.drug.drug.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    // Trang Dashboard (Trang chủ của member) - truyền courses vào model
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "member/dashboard";
    }

    // Trang làm bài đánh giá (khảo sát trắc nghiệm)
    @GetMapping("/assessment")
    public String assessment() {
        return "member/assessment";
    }

    // Trang đặt lịch tư vấn với chuyên viên
    @GetMapping("/consultation")
    public String consultation() {
        return "member/consultation";
    }

    // Trang blog/chia sẻ kinh nghiệm
    @GetMapping("/blog")
    public String blog() {
        return "member/blog";
    }

    // Trang giới thiệu (about)
    @GetMapping("/about")
    public String about() {
        return "member/about";
    }
}
