// 📄 service/SurveyService.java

package com.drug.prevention.service;

import com.drug.prevention.entity.Answer;
import com.drug.prevention.entity.Survey;
import com.drug.prevention.repository.AnswerRepository;
import com.drug.prevention.repository.SurveyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepo;
    private final AnswerRepository answerRepo;

    public SurveyService(SurveyRepository surveyRepo, AnswerRepository answerRepo) {
        this.surveyRepo = surveyRepo;
        this.answerRepo = answerRepo;
    }

    public List<Survey> getAllSurveys() {
        return surveyRepo.findAll();
    }

    public void saveAnswers(List<Answer> answers) {
        answerRepo.saveAll(answers);
    }
}
