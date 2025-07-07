package com.drug.drug.service;

import com.drug.drug.entity.Question;
import com.drug.drug.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) { 
        this.questionRepository = questionRepository; 
    }

    // Lấy tất cả câu hỏi theo testId (giữ nguyên)
    public List<Question> getQuestionsByTestId(Long testId) { 
        return questionRepository.findByTestId(testId); 
    }

    // Lấy tối đa 10 câu hỏi theo testId (MỚI)
    public List<Question> getTop10QuestionsByTestId(Long testId) {
        return questionRepository.findTop10ByTestId(testId);
    }

    public Optional<Question> getQuestion(Long id) { 
        return questionRepository.findById(id); 
    }

    public Question saveQuestion(Question question) { 
        return questionRepository.save(question); 
    }

    public void deleteQuestion(Long id) { 
        questionRepository.deleteById(id); 
    }
}
