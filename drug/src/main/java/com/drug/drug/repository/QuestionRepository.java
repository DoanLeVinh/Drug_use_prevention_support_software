package com.drug.drug.repository;

import com.drug.drug.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Lấy tối đa 10 câu hỏi của một bài khảo sát (nếu muốn lấy tất cả thì chỉ cần findByTestId)
    List<Question> findTop10ByTestId(Long testId);

    // Lấy tất cả câu hỏi của một bài khảo sát
    List<Question> findByTestId(Long testId);
}
