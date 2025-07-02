package com.drug.drug.service;

import com.drug.drug.entity.Question;
import com.drug.drug.entity.Answer;
import com.drug.drug.repository.QuestionRepository;
import com.drug.drug.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestDoService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public List<Question> getQuestionsWithAnswers(Long testId) {
List<Question> questions = questionRepository.findTop10ByTestId(testId);

    // Giới hạn 10 câu hỏi đầu tiên
    if (questions.size() > 10) {
        questions = questions.subList(0, 10);
    }

    for (Question question : questions) {
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());
        question.setAnswers(answers);
    }
    return questions;
}

}
