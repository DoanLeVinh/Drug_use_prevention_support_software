package com.drug.drug.repository;

import com.drug.drug.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    // Lấy danh sách bài test theo danh sách id
    List<Test> findByIdIn(List<Long> ids);
    List<Test> findAllByOrderByCreatedAtDesc(); 
}
