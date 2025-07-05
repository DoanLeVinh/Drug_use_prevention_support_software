package com.drug.drug.service;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.TestResult;
import com.drug.drug.entity.PastCourse;
import com.drug.drug.entity.Course;
import com.drug.drug.entity.Test;
import com.drug.drug.repository.BookingRepository;
import com.drug.drug.repository.TestResultRepository;
import com.drug.drug.repository.PastCourseRepository;
import com.drug.drug.repository.CourseRepository;
import com.drug.drug.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserHistoryService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private PastCourseRepository pastCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestRepository testRepository;

    public List<Map<String, Object>> getUserHistory(Long userId) {
        List<Map<String, Object>> history = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // 1. Lịch sử đặt lịch (bookings)
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        for (Booking b : bookings) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", "Đặt lịch tư vấn");
            map.put("description", "Dịch vụ: " + b.getService() +
                                   " | Ngày: " + (b.getBookingDate() != null ? b.getBookingDate().format(dateFormatter) : "") +
                                   " | Trạng thái: " + (b.getStatus() != null ? b.getStatus() : "Chưa xác định") +
                                   " | Ghi chú: " + (b.getNote() != null ? b.getNote() : "Không có"));
            map.put("time", b.getCreatedAt() != null ? b.getCreatedAt().format(dateTimeFormatter) : "");
            history.add(map);
        }

        // 2. Lịch sử làm bài test (test_results) + join test
        List<TestResult> tests = testResultRepository.findByUserId(userId);
        for (TestResult t : tests) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", "Làm bài kiểm tra");

            String testName = "";
            String testDesc = "";
            if (t.getTestId() != null) {
                Optional<Test> testOpt = testRepository.findById(t.getTestId());
                if (testOpt.isPresent()) {
                    Test test = testOpt.get();
                    testName = test.getName();
                    testDesc = test.getDescription();
                }
            }

            map.put("description", "Tên bài test: " + (!testName.isEmpty() ? testName : "Không rõ") +
                    " | Điểm: " + t.getScore() + "/" + t.getTotalQuestions() +
                    (testDesc.isEmpty() ? "" : " | Mô tả: " + testDesc));
            map.put("time", t.getSubmittedAt() != null ? t.getSubmittedAt().format(dateTimeFormatter) : "");
            history.add(map);
        }

        // 3. Khóa học đã hoàn thành (past_courses) + join course
        List<PastCourse> pastCourses = pastCourseRepository.findByUserIdOrderByAccessedAtDesc(userId);
        for (PastCourse pc : pastCourses) {
            Map<String, Object> map = new HashMap<>();
            map.put("type", "Khóa học đã hoàn thành");

            String courseName = "";
            String courseDesc = "";
            if (pc.getCourseId() != null) {
                Optional<Course> courseOpt = courseRepository.findById(pc.getCourseId());
                if (courseOpt.isPresent()) {
                    Course course = courseOpt.get();
                    courseName = course.getName();
                    courseDesc = course.getDescription();
                }
            }
            map.put("description", "Tên: " + (!courseName.isEmpty() ? courseName : pc.getName()) +
                    (courseDesc.isEmpty() ? "" : " | Mô tả: " + courseDesc) +
                    (pc.getAction() != null ? " | Kết quả: " + pc.getAction() : ""));
            map.put("time", pc.getAccessedAt() != null ? pc.getAccessedAt().format(dateTimeFormatter) : "");
            history.add(map);
        }

        // Sắp xếp giảm dần theo thời gian (nếu cần)
        history.sort((a, b) -> b.get("time").toString().compareTo(a.get("time").toString()));

        return history;
    }
}
