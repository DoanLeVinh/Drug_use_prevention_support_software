package com.drug.drug.controller;

import com.drug.drug.entity.User;
import com.drug.drug.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // ================= Dashboard & Thông tin admin =================

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String profile() {
        return "admin/profile";
    }

    @GetMapping("/statistics")
    public String statistics() {
        return "admin/statistics";
    }

    // ================= CRUD USER =================

    // Hiển thị danh sách tài khoản user
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";  // View: /templates/admin/user-list.html
    }

    // Hiển thị form thêm mới tài khoản user
    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    // Xử lý lưu tài khoản mới
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        if (user.getUsername() == null || user.getUsername().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
            model.addAttribute("error", "Username và password là bắt buộc!");
            return "admin/user-form";
        }
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "admin/user-form";
        }
        if (user.getRole() == null || user.getRole().isBlank()) user.setRole("member");
        userService.register(user);
        return "redirect:/admin/users";
    }

    // Hiển thị form sửa tài khoản user
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/user-form";
        }
        return "redirect:/admin/users";
    }

    // Xử lý lưu cập nhật tài khoản user
    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
        if (user.getUsername() == null || user.getUsername().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
            model.addAttribute("error", "Username và password là bắt buộc!");
            return "admin/user-form";
        }
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    // Xóa tài khoản user
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
