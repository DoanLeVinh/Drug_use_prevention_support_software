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
        return "login"; // Đã sửa thành chữ thường
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

    @GetMapping("/dashboard") 
    public String dashboard() {
        return "staff/dashboard";
    }

    @GetMapping("/layout")
    public String layout() {
        return "staff/layout";
    }

    @GetMapping("/user-management")
    public String userManagement() {
        return "staff/user-management";
    }
    @GetMapping("/appointments")
    public String appointments() {
        return "staff/appointments";
    }
}