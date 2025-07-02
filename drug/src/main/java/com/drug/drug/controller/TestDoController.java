package com.drug.drug.controller;

import com.drug.drug.entity.Question;
import com.drug.drug.service.TestDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class TestDoController {

    @Autowired
    private TestDoService testDoService;

    @GetMapping("/tests/{id}/do")
    public String doTest(@PathVariable("id") Long testId, Model model) {
        List<Question> questions = testDoService.getQuestionsWithAnswers(testId);
        model.addAttribute("questions", questions);
        return "member/test_do";
    }
}
