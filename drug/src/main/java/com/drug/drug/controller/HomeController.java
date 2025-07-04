package com.drug.drug.controller;

import com.drug.drug.entity.BlogPost;
import com.drug.drug.entity.Booking;
import com.drug.drug.entity.User;
import com.drug.drug.service.BlogPostService;
import com.drug.drug.service.BookingService;
import com.drug.drug.service.CourseService;
import com.drug.drug.service.TestService;
import com.drug.drug.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private UserRepository userRepository;

    // Dashboard
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "member/dashboard";
    }

    // Đánh giá
    @GetMapping("/assessment")
    public String assessment(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return "member/assessment";
    }

    // Đặt lịch - form
    @GetMapping("/consultation")
    public String consultationForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "member/consultation";
    }

    // Xử lý submit đặt lịch
    @PostMapping("/consultation")
    public String submitConsultation(
            @ModelAttribute("booking") Booking booking,
            Model model,
            Principal principal
    ) {
        try {
            String username = principal.getName();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                model.addAttribute("error", "Không tìm thấy thông tin tài khoản.");
                model.addAttribute("booking", booking);
                return "member/consultation";
            }

            booking.setUser(user);
            booking.setStatus("Chờ xác nhận");
            bookingService.saveBooking(booking);

            model.addAttribute("success", "Đặt lịch thành công! Chúng tôi sẽ liên hệ với bạn sớm.");
            model.addAttribute("booking", new Booking());
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra, vui lòng thử lại.");
            model.addAttribute("booking", booking);
        }
        return "member/consultation";
    }

    // Trang giới thiệu
    @GetMapping("/about")
    public String about() {
        return "member/about";
    }
}
