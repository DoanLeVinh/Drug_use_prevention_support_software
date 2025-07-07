package com.drug.drug.controller;

import com.drug.drug.entity.Course;
import com.drug.drug.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    @Autowired
    private CourseService courseService;

    // Hiển thị danh sách khóa học
    @GetMapping("")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/courses";
    }

    // Hiển thị form thêm mới/sửa khóa học
    @GetMapping("/edit")
    public String showEditForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("course", courseService.getCourseById(id));
        } else {
            model.addAttribute("course", new Course());
        }
        return "admin/course-form";
    }

    // Lưu (thêm mới/cập nhật)
    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course) {
        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    // Xóa khóa học
    @GetMapping("/delete")
    public String deleteCourse(@RequestParam("id") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }
}
