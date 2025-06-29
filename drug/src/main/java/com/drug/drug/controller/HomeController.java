package com.drug.drug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Trang Dashboard (Trang chủ của member)
    @GetMapping({"/", "/dashboard"})
    public String dashboard() {
        return "member/dashboard";
    }

    // Trang khóa học
    @GetMapping("/courses")
    public String courses() {
        return "member/courses";
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
