package com.drug.drug.repository;

import com.drug.drug.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // <-- THÊM DÒNG NÀY

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByIdIn(List<Long> ids);
}
