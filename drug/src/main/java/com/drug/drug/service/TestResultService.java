// TestResultService.java
package com.drug.drug.service;

import com.drug.drug.entity.TestResult;
import com.drug.drug.repository.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultService {
    private final TestResultRepository testResultRepository;

    public TestResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    public List<TestResult> findByUserId(Long userId) {
        return testResultRepository.findByUserId(userId);
    }
}
