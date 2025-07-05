package com.drug.drug.service;

import com.drug.drug.entity.Test;
import com.drug.drug.entity.TestResult;
import com.drug.drug.entity.Question;
import com.drug.drug.entity.Answer;
import com.drug.drug.repository.TestRepository;
import com.drug.drug.repository.TestResultRepository;
import com.drug.drug.repository.QuestionRepository;
import com.drug.drug.repository.AnswerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    // Lấy tất cả các bài test/khảo sát
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    // Lưu kết quả làm bài test/khảo sát
    @Transactional
    public TestResult saveTestResult(TestResult result) {
        return testResultRepository.save(result);
    }

    // Lấy kết quả các bài test theo user
    public List<TestResult> getResultsByUser(Long userId) {
        return testResultRepository.findByUserId(userId);
    }

    // Lấy kết quả theo từng bài test
    public List<TestResult> getResultsByTest(Long testId) {
        return testResultRepository.findByTestId(testId);
    }

    // Lấy danh sách câu hỏi kèm đáp án của 1 test (tối đa 10 câu)
    public List<Question> getQuestionsWithAnswers(Long testId) {
        List<Question> questions = questionRepository.findTop10ByTestId(testId);
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
