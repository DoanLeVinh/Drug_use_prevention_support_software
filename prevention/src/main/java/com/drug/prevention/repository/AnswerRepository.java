// 📄 repository/AnswerRepository.java

package com.drug.prevention.repository;

import com.drug.prevention.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
