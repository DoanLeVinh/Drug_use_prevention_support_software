package com.drug.drug.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // chỉ cần import TestService
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drug.drug.entity.Question;
import com.drug.drug.entity.Test;
import com.drug.drug.entity.TestResult;
import com.drug.drug.entity.User;
import com.drug.drug.repository.TestResultRepository;
import com.drug.drug.service.TestService;
import com.drug.drug.service.UserService;

@Controller
public class TestController {

    @Autowired
    private TestService testService; // đã gộp tất cả function liên quan test/result/question vào đây

    @Autowired
    private UserService userService;


    @Autowired
    private TestResultRepository testResultRepository;

    // Hiển thị danh sách các bài test/khảo sát
    @GetMapping("/tests")
    public String showTests(Model model) {
        List<Test> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        return "assessment"; // Tên file Thymeleaf (assessment.html)
    }

    // Trang làm bài test
//    @GetMapping("/tests/{id}/do")
//    public String doTest(@PathVariable("id") Long testId, Model model) {
//        List<Question> questions = testService.getQuestionsWithAnswers(testId);
//        model.addAttribute("questions", questions);
//        model.addAttribute("testId", testId);
//        return "survey";
//    }
    // Trong file: TestController.java
    @GetMapping("/tests/{id}/do")
    public String doTest(@PathVariable("id") Long testId, Model model) {
        List<Question> questions = testService.getQuestionsWithAnswers(testId);
        model.addAttribute("questions", questions);
        model.addAttribute("testId", testId);
        // Đổi tên file view để khớp với yêu cầu của bạn
        return "member/survey"; // <<<< SỬA TÊN FILE NÀY
    }

    // API: Khi bắt đầu làm bài (ấn xác nhận trên modal) - lưu lịch sử bắt đầu (nếu muốn)
    @PostMapping("/tests/{id}/start")
    @ResponseBody
    public String startTest(@PathVariable("id") Long testId, Principal principal) {
        if (principal == null) return "fail";
        User user = userService.findByUsername(principal.getName());
        if (user == null) return "fail";
        // Nếu muốn log lịch sử bắt đầu làm bài thì thêm code tại đây (ví dụ: tạo TestResult tạm với startedAt)
        return "success";
    }

    // API: Khi nộp bài, lưu kết quả vào test_results
    @PostMapping("/tests/{id}/submit")
    @ResponseBody
    public String submitTest(@PathVariable("id") Long testId,
                             @RequestBody Map<String, Object> data,
                             Principal principal) {
        if (principal == null) return "fail";
        User user = userService.findByUsername(principal.getName());
        if (user == null) return "fail";

        try {
            Integer score = parseInt(data.get("score"));
            Integer totalQuestions = parseInt(data.get("totalQuestions"));
            Integer correctAnswers = parseInt(data.get("correctAnswers"));
            String startedAtStr = (String) data.get("startedAt");
            Integer duration = parseInt(data.get("duration"));

            // Parse thời gian bắt đầu (nếu có)
            LocalDateTime startedAt = null;
            if (startedAtStr != null && !startedAtStr.isEmpty()) {
                // Đảm bảo dữ liệu từ FE là ISO_LOCAL_DATE_TIME (nếu lỗi thì cần custom parse)
                startedAt = LocalDateTime.parse(startedAtStr.replace("Z", ""));
            }

            // Tạo bản ghi kết quả test
            TestResult result = new TestResult();
            result.setUserId(user.getId());
            result.setTestId(testId);
            result.setScore(score);
            result.setTotalQuestions(totalQuestions);
            result.setCorrectAnswers(correctAnswers);
            result.setStartedAt(startedAt);
            result.setSubmittedAt(LocalDateTime.now());
            result.setDuration(duration);

            testService.saveTestResult(result);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    // Helper function để parse số an toàn (tránh lỗi khi kiểu dữ liệu JSON về là Double hoặc String)
    private Integer parseInt(Object val) {
        if (val == null) return null;
        if (val instanceof Integer) return (Integer) val;
        if (val instanceof Double) return ((Double) val).intValue();
        if (val instanceof String) return Integer.parseInt((String) val);
        return null;
    }

    @PostMapping("/survey/submit")
    @ResponseBody
    public Map<String, String> handleSubmitSurvey(@RequestBody Map<String, Object> payload, Principal principal) {
        if (principal == null) {
            return Map.of("error", "User not logged in");
        }

        try {
            User user = userService.findByUsername(principal.getName());
            Long testId = Long.parseLong(payload.get("testId").toString());
            Integer totalScore = (Integer) payload.get("totalScore");

            // Lưu kết quả vào DB
            TestResult result = new TestResult();
            result.setUserId(user.getId());
            result.setTestId(testId);
            result.setScore(totalScore); // Lưu tổng điểm
            result.setSubmittedAt(LocalDateTime.now());
            // Bạn có thể lưu thêm các thông tin khác nếu cần

            TestResult savedResult = testResultRepository.save(result);

            // Trả về URL để redirect tới trang kết quả
            return Map.of("redirectUrl", "/member/survey-result/" + savedResult.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Failed to save survey result.");
        }
    }
}
