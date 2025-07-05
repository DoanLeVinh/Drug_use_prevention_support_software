package com.drug.drug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // BỔ SUNG import này!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.drug.drug.model.SurveyForm; // BỔ SUNG import này!

@Controller
@RequestMapping("/staff")
public class StaffController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "staff/dashboard";
    }

    @GetMapping("/user-management")
    public String userManagement() {
        return "staff/user-management";
    }

    @GetMapping("/appointments")
    public String appointments() {
        return "staff/appointments";
    }

    @GetMapping("/courses")
    public String courses() {
        return "staff/courses";
    }

    @GetMapping("/events")
    public String events() {
        return "staff/events";
    }

    @GetMapping("/surveys")
    public String surveys(Model model) {
        // Lấy dữ liệu cho trang surveys (nếu có)
        model.addAttribute("newSurvey", new SurveyForm());
        return "staff/surveys";
    }

    @PostMapping("/surveys")
    public String createSurvey(@ModelAttribute("newSurvey") SurveyForm surveyForm, Model model) {
        // (Bạn có thể validate, lưu vào DB, etc.)
        // Sau khi lưu, có thể redirect về trang danh sách khảo sát
        return "redirect:/staff/surveys";
    }

    @GetMapping("/consultants")
    public String consultants() {
        return "staff/consultants";
    }

    @GetMapping("/profile")
    public String profile() {
        return "staff/profile";
    }
}
