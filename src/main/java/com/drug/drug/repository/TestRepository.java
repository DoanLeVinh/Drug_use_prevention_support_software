package com.drug.drug.repository;

import com.drug.drug.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
    // Thêm phương thức custom nếu cần
}
