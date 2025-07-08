// 📄 repository/QuestionRepository.java

package com.drug.prevention.repository;

import com.drug.prevention.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
