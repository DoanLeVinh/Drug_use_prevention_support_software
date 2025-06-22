package com.drug.drug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping({"/", "/index"})
    public String index() {
        return "member/index";
    }

    @GetMapping("/login")
    public String login() {
        return "Login"; // Đúng tên file login.html (chữ thường)
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/courses")
    public String courses() {
        return "member/courses";
    }
    @GetMapping("/assessment")
    public String assessment() {
        return "member/assessment";
    }
    @GetMapping("/consultation")
    public String consultation() {
        return "member/consultation";
    }
    @GetMapping("/blog")
    public String blog() {
        return "member/blog";
    }
    @GetMapping("/about")
    public String about() {
        return "member/about";
    }
    
}