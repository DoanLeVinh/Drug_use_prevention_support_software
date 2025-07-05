package com.drug.drug.controller;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.PastCourse;
import com.drug.drug.entity.TestResult;
import com.drug.drug.entity.User;
import com.drug.drug.repository.TestResultRepository;
import com.drug.drug.service.BookingService;
import com.drug.drug.service.PastCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private PastCourseService pastCourseService;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/dashboard")
    public String memberDashboard(HttpSession session, Model model) {
        // Lấy thông tin user từ session (đã được lưu khi đăng nhập thành công)
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            // Nếu không có user trong session, chuyển hướng về trang đăng nhập
            return "redirect:/login";
        }

        // Lấy danh sách các khóa học đã tham gia
        List<PastCourse> attendedCourses = pastCourseService.getPastCoursesByUser(currentUser.getId());

        // Lấy danh sách lịch hẹn (sắp tới)
        // Lưu ý: BookingService của bạn chưa có hàm lấy lịch hẹn sắp tới,
        // tạm thời chúng ta lấy tất cả, bạn có thể nâng cấp sau.
        List<Booking> appointments = bookingService.getBookingsByUser(currentUser);

        // Đưa dữ liệu vào Model để Thymeleaf có thể hiển thị
        model.addAttribute("user", currentUser);
        model.addAttribute("attendedCourses", attendedCourses);
        model.addAttribute("appointments", appointments);

        // Trả về file HTML tương ứng
        return "member/member-dashboard"; // -> templates/member/member-dashboard.html
    }
    @GetMapping("/survey-result/{resultId}")
    public String showSurveyResult(@PathVariable Long resultId, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<TestResult> resultOpt = testResultRepository.findById(resultId);
        if (resultOpt.isEmpty() || !resultOpt.get().getUserId().equals(currentUser.getId())) {
            // Kết quả không tồn tại hoặc không phải của user này
            return "redirect:/member/dashboard";
        }

        TestResult result = resultOpt.get();
        model.addAttribute("result", result);

        // Xử lý logic đề xuất dựa trên điểm số
        int score = result.getScore();
        String riskLevel;
        String recommendation;

        if (score <= 2) {
            riskLevel = "Nguy cơ thấp";
            recommendation = "Kiến thức của bạn rất tốt! Hãy tiếp tục duy trì và tìm hiểu thêm các khóa học nâng cao.";
        } else if (score <= 5) {
            riskLevel = "Nguy cơ trung bình";
            recommendation = "Bạn có một vài dấu hiệu rủi ro. Hãy xem các khóa học về kỹ năng phòng tránh và cân nhắc đặt lịch tư vấn với chuyên gia.";
        } else {
            riskLevel = "Nguy cơ cao";
            recommendation = "Bạn đang ở mức nguy cơ cao. Chúng tôi khuyến khích bạn nên ĐẶT LỊCH TƯ VẤN với chuyên gia ngay để được hỗ trợ kịp thời.";
        }

        model.addAttribute("riskLevel", riskLevel);
        model.addAttribute("recommendation", recommendation);

        return "member/survey-result"; // -> templates/member/survey-result.html
    }
}
