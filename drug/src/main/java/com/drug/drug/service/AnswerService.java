package com.drug.drug.service;
import com.drug.drug.entity.Answer;
import com.drug.drug.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    public AnswerService(AnswerRepository answerRepository) { this.answerRepository = answerRepository; }
    public List<Answer> getAnswersByQuestionId(Long questionId) { return answerRepository.findByQuestionId(questionId); }
    public Optional<Answer> getAnswer(Long id) { return answerRepository.findById(id); }
    public Answer saveAnswer(Answer answer) { return answerRepository.save(answer); }
    public void deleteAnswer(Long id) { answerRepository.deleteById(id); }
}
