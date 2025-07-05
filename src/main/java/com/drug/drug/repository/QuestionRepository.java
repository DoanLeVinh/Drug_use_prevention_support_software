package com.drug.drug.repository;

import com.drug.drug.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
// QuestionRepository.java
List<Question> findTop10ByTestId(Long testId);
}
