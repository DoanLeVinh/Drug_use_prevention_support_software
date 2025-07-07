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
import java.util.Optional; // Thêm dòng này

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

    // ================== BỔ SUNG CHO CRUD TEST ===================
    // Lưu hoặc cập nhật bài test
    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    // Lấy 1 bài test theo id
    public Optional<Test> getTest(Long id) {
        return testRepository.findById(id);
    }

    // Xóa bài test theo id
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    // ========== CRUD CHO QUESTION ==========
public List<Question> getQuestionsByTestId(Long testId) {
    return questionRepository.findByTestId(testId);
}

public Question getQuestionById(Long id) {
    return questionRepository.findById(id).orElse(null);
}

public Question saveQuestion(Question question) {
    return questionRepository.save(question);
}

public void deleteQuestion(Long id) {
    questionRepository.deleteById(id);
}

// ========== CRUD CHO ANSWER ==========
public List<Answer> getAnswersByQuestionId(Long questionId) {
    return answerRepository.findByQuestionId(questionId);
}

public Answer getAnswerById(Long id) {
    return answerRepository.findById(id).orElse(null);
}

public Answer saveAnswer(Answer answer) {
    return answerRepository.save(answer);
}

public void deleteAnswer(Long id) {
    answerRepository.deleteById(id);
}

}
