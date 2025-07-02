package com.drug.drug.controller;

import com.drug.drug.entity.Test;
import com.drug.drug.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/tests")
    public String showTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        return "assessment"; // Tên file Thymeleaf (assessment.html)
    }
}
