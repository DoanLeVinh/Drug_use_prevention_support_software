package com.drug.drug.controller;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.Course;
import com.drug.drug.entity.User;
import com.drug.drug.entity.TestResult;
import com.drug.drug.entity.PastCourse;
import com.drug.drug.entity.Test;
import com.drug.drug.service.BookingService;
import com.drug.drug.service.CourseService;
import com.drug.drug.service.UserService;
import com.drug.drug.service.TestResultService;
import com.drug.drug.service.PastCourseService;
import com.drug.drug.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final BookingService bookingService;
    private final CourseService courseService;
    private final UserService userService;
    private final TestResultService testResultService;
    private final PastCourseService pastCourseService;
    private final TestService testService;

    public StaffController(
            BookingService bookingService,
            CourseService courseService,
            UserService userService,
            TestResultService testResultService,
            PastCourseService pastCourseService,
            TestService testService
    ) {
        this.bookingService = bookingService;
        this.courseService = courseService;
        this.userService = userService;
        this.testResultService = testResultService;
        this.pastCourseService = pastCourseService;
        this.testService = testService;
    }

    // ====== Quản lý user (member) và hiển thị chi tiết theo kiểu reload cùng trang ======
    @GetMapping("/user-management")
    public String userManagement(Model model) {
        List<User> members = userService.findUsersByRole("member");
        model.addAttribute("members", members);
        return "staff/user-management";
    }

    @GetMapping("/user-management/detail/{id}")
    public String userManagementDetail(@PathVariable Long id, Model model) {
        List<User> members = userService.findUsersByRole("member");
        User selectedUser = userService.getUserById(id);
        List<Booking> bookings = bookingService.findByUserId(id);
        List<PastCourse> pastCourses = pastCourseService.findByUserId(id);
        List<TestResult> testResults = testResultService.findByUserId(id);

        model.addAttribute("members", members);
        model.addAttribute("selectedUser", selectedUser);
        model.addAttribute("bookings", bookings);
        model.addAttribute("pastCourses", pastCourses);
        model.addAttribute("testResults", testResults);

        return "staff/user-management";
    }

    // ====== Quản lý lịch hẹn ======
    @GetMapping("/appointments")
    public String appointments(Model model) {
        model.addAttribute("appointments", bookingService.getAllBookings());
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

    // ====== Quản lý khóa học ======
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("course", new Course());
        return "staff/courses";
    }

    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute Course course,
                             RedirectAttributes redirectAttributes) {
        try {
            if (course.getId() != null) {
                Course existingCourse = courseService.getCourseById(course.getId());
                if (existingCourse != null) {
                    course.setCreatedAt(existingCourse.getCreatedAt());
                }
            } else {
                course.setCreatedAt(LocalDateTime.now());
            }
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("successMessage", "Lưu khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu khóa học: " + e.getMessage());
        }
        return "redirect:/staff/courses";
    }

    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa khóa học thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa khóa học: " + e.getMessage());
        }
        return "redirect:/staff/courses";
    }

    // ====== Các trang staff khác ======
    @GetMapping("/dashboard")
    public String dashboard() {
        return "staff/dashboard";
    }

    @GetMapping("/events")
    public String events() {
        return "staff/events";
    }

    // ====== ĐÃ XÓA mapping /surveys để tránh xung đột ======
    // @GetMapping("/surveys")
    // public String surveys(Model model) {
    //     model.addAttribute("newSurvey", new SurveyForm());
    //     return "staff/surveys";
    // }
    // @PostMapping("/surveys")
    // public String createSurvey(@ModelAttribute("newSurvey") SurveyForm surveyForm) {
    //     // Xử lý tạo survey ở đây
    //     System.out.println(">>> Creating new survey: " + surveyForm);
    //     return "redirect:/staff/surveys";
    // }

    @GetMapping("/consultants")
    public String consultants() {
        return "staff/consultants";
    }

    @GetMapping("/profile")
    public String profile() {
        return "staff/profile";
    }
}
