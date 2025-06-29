package com.drug.drug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Trang đăng ký
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Trang quên mật khẩu
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    // Có thể thêm logout redirect nếu muốn
    // @GetMapping("/logout-success")
    // public String logout() {
    //     return "redirect:/login?logout";
    // }
}
