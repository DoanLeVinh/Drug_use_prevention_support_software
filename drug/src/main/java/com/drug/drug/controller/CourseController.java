package com.drug.drug.controller;

import com.drug.drug.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
