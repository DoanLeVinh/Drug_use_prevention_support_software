package com.drug.drug.controller;

import com.drug.drug.entity.User;
import com.drug.drug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Nếu dùng BCrypt, cần dependency spring-boot-starter-security
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Trang đăng nhập
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Trang đăng ký (GET)
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("error", null);
        model.addAttribute("success", null);
        return "register";
    }

    // Xử lý đăng ký (POST)
    @PostMapping("/register")
    public String doRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String email,
            @RequestParam(required = false) String sdt,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String birthday, // yyyy-MM-dd
            Model model
    ) {
        // Kiểm tra username/email đã tồn tại chưa
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "register";
        }
        if (email != null && !email.isEmpty() && userRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email đã được sử dụng!");
            return "register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "register";
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
        user.setEmail(email);
        user.setSdt(sdt);
        user.setSex(sex);
        user.setAddress(address);
        user.setRole("member");
        if (birthday != null && !birthday.isEmpty()) {
            user.setBirthday(LocalDate.parse(birthday));
        }

        userRepository.save(user);

        model.addAttribute("success", "Đăng ký thành công! Hãy đăng nhập.");
        return "register";
    }

    // Trang quên mật khẩu
    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }
}
