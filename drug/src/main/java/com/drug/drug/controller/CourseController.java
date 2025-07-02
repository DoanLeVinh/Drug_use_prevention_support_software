package com.drug.drug.controller;

import com.drug.drug.entity.Course;
import com.drug.drug.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Trang liệt kê tất cả các khóa học cho member, đường dẫn là /courses
    @GetMapping("/courses")
    public String showCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "member/courses"; // view nằm ở src/main/resources/templates/member/courses.html
    }

    // Trang chi tiết khóa học (vd: /courses/1)
    @GetMapping("/courses/{id}")
    public String showCourseDetail(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            // Nếu không tìm thấy course thì chuyển hướng về trang danh sách
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        return "member/courses-details"; // Tên file HTML của bạn
    }
}
