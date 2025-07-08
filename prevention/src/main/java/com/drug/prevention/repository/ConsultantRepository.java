// 📄 repository/ConsultantRepository.java

package com.drug.prevention.repository;

import com.drug.prevention.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
}
