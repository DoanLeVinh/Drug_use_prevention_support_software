// 📄 controller/SurveyController.java

package com.drug.prevention.controller;

import com.drug.prevention.entity.Answer;
import com.drug.prevention.entity.Survey;
import com.drug.prevention.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @GetMapping
    public List<Survey> getSurveys() {
        return surveyService.getAllSurveys();
    }

    @PostMapping("/submit")
    public String submitAnswers(@RequestBody List<Answer> answers) {
        surveyService.saveAnswers(answers);
        return "Đã ghi nhận câu trả lời!";
    }
}
