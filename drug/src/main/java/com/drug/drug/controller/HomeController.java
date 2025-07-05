package com.drug.drug.controller;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.User;
import com.drug.drug.service.*;
import com.drug.drug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    private final CourseService courseService;
    private final TestService testService;
    private final BookingService bookingService;
    private final BlogPostService blogPostService;
    private final UserRepository userRepository;
    private final UserHistoryService userHistoryService;

    @Autowired
    public HomeController(CourseService courseService,
                        TestService testService,
                        BookingService bookingService,
                        BlogPostService blogPostService,
                        UserRepository userRepository,
                        UserHistoryService userHistoryService) {
        this.courseService = courseService;
        this.testService = testService;
        this.bookingService = bookingService;
        this.blogPostService = blogPostService;
        this.userRepository = userRepository;
        this.userHistoryService = userHistoryService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "member/dashboard";
    }

    @GetMapping("/assessment")
    public String assessment(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return "member/assessment";
    }

    @GetMapping("/consultation")
    public String consultationForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "member/consultation";
    }

    @PostMapping("/consultation")
    public String submitConsultation(
            @ModelAttribute("booking") Booking booking,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Validate user
            String username = principal.getName();
            Optional<User> userOptional = userRepository.findByUsername(username);
            
            if (!userOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để đặt lịch");
                return "redirect:/login";
            }

            // Validate booking data
            if (booking.getBookingDate() == null || booking.getBookingTime() == null || 
                booking.getConsultant() == null || booking.getService() == null) {
                redirectAttributes.addFlashAttribute("error", "Vui lòng điền đầy đủ thông tin đặt lịch");
                return "redirect:/consultation";
            }

            // Set user and status
            User user = userOptional.get();
            booking.setUser(user);
            booking.setStatus("Chờ xác nhận");
            booking.setCreatedAt(LocalDateTime.now());

            // Save booking
            Booking savedBooking = bookingService.saveBooking(booking);
            
            if (savedBooking != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Đặt lịch thành công! Mã đặt lịch: " + savedBooking.getId());
            } else {
                redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu thông tin đặt lịch");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi hệ thống: " + e.getMessage());
        }
        
        return "redirect:/consultation";
    }

    @GetMapping("/about")
    public String about() {
        return "member/about";
    }

    @GetMapping("/history")
    public String userHistory(Model model, Principal principal) {
        try {
            String username = principal.getName();
            Optional<User> userOptional = userRepository.findByUsername(username);
            
            if (!userOptional.isPresent()) {
                return "redirect:/login?error=user_not_found";
            }

            User user = userOptional.get();
            List<Map<String, Object>> history = userHistoryService.getUserHistory(user.getId());
            
            // Add bookings to history
            List<Booking> bookings = bookingService.getBookingsByUser(user);
            model.addAttribute("bookings", bookings);
            model.addAttribute("history", history);
            
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải lịch sử: " + e.getMessage());
        }
        
        return "member/user_history";
    }

    // New endpoint to handle booking status updates
    @PostMapping("/booking/{id}/update-status")
    public String updateBookingStatus(
            @PathVariable Long id,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {
        
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(id, status);
            
            if (updatedBooking != null) {
                redirectAttributes.addFlashAttribute("success", 
                    "Cập nhật trạng thái thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Không tìm thấy đặt lịch với ID: " + id);
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi khi cập nhật: " + e.getMessage());
        }
        
        return "redirect:/history";
    }
}