package com.drug.drug.controller;

import com.drug.drug.entity.Course;
import com.drug.drug.entity.User;
import com.drug.drug.service.CourseService;
import com.drug.drug.service.PastCourseService;
import com.drug.drug.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private PastCourseService pastCourseService;

    @Autowired
    private UserService userService;

    // Trang liệt kê tất cả các khóa học cho member
    @GetMapping("/courses")
    public String showCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "member/courses";
    }

    // API: Bắt đầu học (gọi bằng fetch JS khi user xác nhận)
    @PostMapping("/courses/{id}/start")
    @ResponseBody
    public String startCourse(@PathVariable("id") Long id, Principal principal) {
        Course course = courseService.getCourseById(id);
        if (course == null || principal == null) {
            return "fail";
        }
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return "fail";
        }
        pastCourseService.logCourseAccess(
            user.getId(),
            course.getId(),
            course.getName(),
            course.getDescription(),
            course.getTargetObject(),
            course.getTopic(),
            course.getVideo1(),
            course.getVideo2(),
            course.getVideo3(),
            course.getParticipants(),
            course.getImage(),
            "STARTED"
        );
        return "success";
    }

    // Trang chi tiết khóa học
    @GetMapping("/courses/{id}")
    public String showCourseDetail(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "member/courses-details";
    }
}
