package com.drug.drug.controller;

import com.drug.drug.entity.Test;
import com.drug.drug.entity.Question;
import com.drug.drug.entity.Answer;
import com.drug.drug.service.TestService;
import com.drug.drug.service.QuestionService;
import com.drug.drug.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/tests")
public class AdminTestController {

    @Autowired
    private TestService testService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    // Hiển thị danh sách tất cả bài test
    @GetMapping("")
    public String listTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        return "admin/test-list";
    }

    // Form thêm/sửa test
    @GetMapping("/edit")
    public String editTest(@RequestParam(required = false) Long id, Model model) {
        Test test = (id != null) ? testService.getTest(id).orElse(new Test()) : new Test();
        model.addAttribute("test", test);
        return "admin/test-edit";
    }

    @PostMapping("/save")
    public String saveTest(@ModelAttribute Test test) {
        testService.saveTest(test);
        return "redirect:/admin/tests";
    }

    @GetMapping("/delete")
    public String deleteTest(@RequestParam Long id) {
        testService.deleteTest(id);
        return "redirect:/admin/tests";
    }

    // Xem chi tiết 1 bài test (10 câu hỏi + đáp án, chỉnh trực tiếp trên giao diện)
    @GetMapping("/{testId}")
    public String viewTestDetail(@PathVariable Long testId, Model model) {
        Test test = testService.getTest(testId).orElse(null);
        List<Question> questions = testService.getQuestionsWithAnswers(testId); // Lấy tối đa 10 câu hỏi, mỗi câu có list đáp án
        model.addAttribute("test", test);
        model.addAttribute("questions", questions);
        return "admin/test-detail";
    }

    // Thêm/sửa câu hỏi (modal hoặc inline)
    @PostMapping("/{testId}/question/save")
    public String saveQuestion(@PathVariable Long testId, @ModelAttribute Question question) {
        question.setTestId(testId);
        questionService.saveQuestion(question);
        return "redirect:/admin/tests/" + testId;
    }

    @GetMapping("/{testId}/question/delete")
    public String deleteQuestion(@PathVariable Long testId, @RequestParam Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/admin/tests/" + testId;
    }

    // Thêm/sửa đáp án (modal hoặc inline)
    @PostMapping("/{testId}/question/{questionId}/answer/save")
    public String saveAnswer(@PathVariable Long testId, @PathVariable Long questionId, @ModelAttribute Answer answer) {
        answer.setQuestionId(questionId);
        answerService.saveAnswer(answer);
        return "redirect:/admin/tests/" + testId;
    }

    @GetMapping("/{testId}/question/{questionId}/answer/delete")
    public String deleteAnswer(@PathVariable Long testId, @PathVariable Long questionId, @RequestParam Long id) {
        answerService.deleteAnswer(id);
        return "redirect:/admin/tests/" + testId;
    }
}
