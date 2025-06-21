package com.drug.drug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // trả về login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // trả về register.html
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password"; // trả về forgot-password.html
    }
}