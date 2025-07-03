package com.drug.drug.controller;

import com.drug.drug.entity.User;
import com.drug.drug.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // Trang đăng nhập
    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
        return "login";
    }

    // Trang đăng ký (GET)
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("error", null);
        model.addAttribute("success", null);
        return "register";
    }

    // Xử lý đăng ký (POST) - Plain text password
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

        // Lưu plain text password
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // <<<< LƯU DẠNG THƯỜNG
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

    // Được gọi sau đăng nhập thành công (Spring Security cấu hình)
    // TỰ ĐỘNG set user vào session để header giao diện biết ai đã login
    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session, Principal principal) {
        if (principal != null) {
            // Nếu repository trả về Optional<User>
            User user = userRepository.findByUsername(principal.getName()).orElse(null);
            if (user != null) {
                session.setAttribute("user", user); // Header lấy ${session.user} để hiển thị
            }
        }
        return "redirect:/";
    }

    // Đăng xuất thủ công (nếu muốn xoá session user)
    @PostMapping("/logout-custom")
    public String logoutCustom(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
