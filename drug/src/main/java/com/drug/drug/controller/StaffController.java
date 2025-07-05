package com.drug.drug.controller;

import com.drug.drug.entity.Booking;
import com.drug.drug.service.BookingService;
import com.drug.drug.model.SurveyForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final BookingService bookingService;

    public StaffController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        List<Booking> appointments = bookingService.getAllBookings();
        model.addAttribute("appointments", appointments);
        return "staff/appointments";
    }

    @PostMapping("/appointments/{id}/update-status")
    public String updateStatus(@PathVariable Long id, 
                             @RequestParam String status,
                             RedirectAttributes redirectAttributes) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, status);
        if (updatedBooking != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy booking!");
        }
        return "redirect:/staff/appointments";
    }

    // Các phương thức khác giữ nguyên
    @GetMapping("/dashboard")
    public String dashboard() {
        return "staff/dashboard";
    }

    @GetMapping("/user-management")
    public String userManagement() {
        return "staff/user-management";
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
        model.addAttribute("newSurvey", new SurveyForm());
        return "staff/surveys";
    }

    @PostMapping("/surveys")
    public String createSurvey(@ModelAttribute("newSurvey") SurveyForm surveyForm, Model model) {
        System.out.println(">>> Creating new survey: " + surveyForm);
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