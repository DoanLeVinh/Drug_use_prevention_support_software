package com.drug.drug.controller;

import com.drug.drug.entity.Test;
import com.drug.drug.entity.Question;
import com.drug.drug.entity.Answer;
import com.drug.drug.service.TestService;
import com.drug.drug.service.QuestionService;
import com.drug.drug.service.AnswerService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff/surveys")
public class SurveyController {

    private final TestService testService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public SurveyController(TestService testService, QuestionService questionService, AnswerService answerService) {
        this.testService = testService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    // Danh sách khảo sát
    @GetMapping
    public String listSurveys(Model model) {
        List<Test> surveys = testService.getAllTests();
        model.addAttribute("surveys", surveys);
        return "staff/surveys/list";
    }

    // Thêm khảo sát
    @GetMapping("/add")
    public String addSurveyForm(Model model) {
        model.addAttribute("survey", new Test());
        return "staff/surveys/add";
    }
    @PostMapping("/add")
    public String addSurvey(@ModelAttribute("survey") Test survey) {
        testService.saveTest(survey);
        return "redirect:/staff/surveys";
    }

    // Sửa khảo sát
    @GetMapping("/edit/{id}")
    public String editSurveyForm(@PathVariable Long id, Model model) {
        Optional<Test> surveyOpt = testService.getTest(id);
        if(surveyOpt.isPresent()) {
            model.addAttribute("survey", surveyOpt.get());
            return "staff/surveys/edit";
        }
        return "redirect:/staff/surveys";
    }
    @PostMapping("/edit")
    public String editSurvey(@ModelAttribute("survey") Test survey) {
        testService.saveTest(survey);
        return "redirect:/staff/surveys";
    }

    // Xóa khảo sát
    @PostMapping("/delete/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        testService.deleteTest(id);
        return "redirect:/staff/surveys";
    }

    // Xem chi tiết khảo sát, chỉ lấy tối đa 10 câu hỏi
    @GetMapping("/detail/{id}")
    public String surveyDetail(@PathVariable Long id, Model model) {
        Optional<Test> surveyOpt = testService.getTest(id);
        if(surveyOpt.isPresent()) {
            // Chỉ lấy tối đa 10 câu hỏi
            List<Question> questions = questionService.getTop10QuestionsByTestId(id);
            // Lấy answers cho mỗi câu hỏi
            for (Question q : questions) {
                List<Answer> answers = answerService.getAnswersByQuestionId(q.getId());
                q.setAnswers(answers);
            }
            model.addAttribute("survey", surveyOpt.get());
            model.addAttribute("questions", questions);
            return "staff/surveys/detail";
        }
        return "redirect:/staff/surveys";
    }

    // ======= Xử lý CRUD cho Question =======
    @PostMapping("/detail/{surveyId}/questions/add")
    public String addQuestion(@PathVariable Long surveyId, @RequestParam String content) {
        Question q = new Question();
        q.setTestId(surveyId);
        q.setContent(content);
        questionService.saveQuestion(q);
        return "redirect:/staff/surveys/detail/" + surveyId;
    }

    @PostMapping("/detail/{surveyId}/questions/edit/{questionId}")
    public String editQuestion(@PathVariable Long surveyId, @PathVariable Long questionId, @RequestParam String content) {
        Optional<Question> qOpt = questionService.getQuestion(questionId);
        if(qOpt.isPresent()) {
            Question q = qOpt.get();
            q.setContent(content);
            questionService.saveQuestion(q);
        }
        return "redirect:/staff/surveys/detail/" + surveyId;
    }

    @PostMapping("/detail/{surveyId}/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable Long surveyId, @PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/staff/surveys/detail/" + surveyId;
    }

    // ======= Xử lý CRUD cho Answer =======
    @PostMapping("/detail/{surveyId}/questions/{questionId}/answers/add")
    public String addAnswer(@PathVariable Long surveyId, @PathVariable Long questionId, @RequestParam String content, @RequestParam(required = false) Boolean isCorrect) {
        Answer a = new Answer();
        a.setQuestionId(questionId);
        a.setContent(content);
        a.setIsCorrect(isCorrect != null ? isCorrect : false);
        answerService.saveAnswer(a);
        return "redirect:/staff/surveys/detail/" + surveyId;
    }

    @PostMapping("/detail/{surveyId}/questions/{questionId}/answers/edit/{answerId}")
    public String editAnswer(@PathVariable Long surveyId, @PathVariable Long questionId, @PathVariable Long answerId, @RequestParam String content, @RequestParam(required = false) Boolean isCorrect) {
        Optional<Answer> aOpt = answerService.getAnswer(answerId);
        if(aOpt.isPresent()) {
            Answer a = aOpt.get();
            a.setContent(content);
            a.setIsCorrect(isCorrect != null ? isCorrect : false);
            answerService.saveAnswer(a);
        }
        return "redirect:/staff/surveys/detail/" + surveyId;
    }

    @PostMapping("/detail/{surveyId}/questions/{questionId}/answers/delete/{answerId}")
    public String deleteAnswer(@PathVariable Long surveyId, @PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return "redirect:/staff/surveys/detail/" + surveyId;
    }
}
