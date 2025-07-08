// 📄 repository/SurveyRepository.java

package com.drug.prevention.repository;

import com.drug.prevention.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
