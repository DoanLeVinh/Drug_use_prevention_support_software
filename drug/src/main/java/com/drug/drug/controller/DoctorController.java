package com.drug.drug.controller;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.MedicalRecord;
import com.drug.drug.service.BookingService;
import com.drug.drug.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final BookingService bookingService;
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public DoctorController(BookingService bookingService, MedicalRecordService medicalRecordService) {
        this.bookingService = bookingService;
        this.medicalRecordService = medicalRecordService;
    }

    // Trang dashboard cho doctor
    @GetMapping({"", "/", "/dashboard"})
    public String dashboard() {
        return "doctor/dashboard";
    }

    // Hiển thị tất cả lịch hẹn của bác sĩ (mọi trạng thái)
    @GetMapping("/appointments")
    public String appointments(Model model, Principal principal) {
        String consultantUsername = principal.getName();
        List<Booking> appointments = bookingService.getBookingsByConsultant(consultantUsername);
        model.addAttribute("appointments", appointments);
        return "doctor/appointments";
    }

    // Hiển thị lịch hẹn HOÀN THÀNH để cập nhật/xem hồ sơ bệnh án
    @GetMapping("/completed-appointments")
    public String completedAppointments(Model model, Principal principal) {
        String consultantUsername = principal.getName();
        List<Booking> completedBookings = bookingService.getCompletedBookingsByConsultant(consultantUsername);
        model.addAttribute("bookings", completedBookings);
        return "doctor/patient-bookings"; // view này như hướng dẫn ở trên
    }

    // Hiển thị form cập nhật hoặc thêm mới hồ sơ bệnh án
    @GetMapping("/medical-record/{bookingId}/edit")
    public String editMedicalRecord(@PathVariable Long bookingId, Model model) {
        Optional<MedicalRecord> recordOpt = medicalRecordService.findByBookingId(bookingId);
        MedicalRecord record = recordOpt.orElseGet(MedicalRecord::new);
        // Nếu record mới, gán bookingId cho booking
        if (record.getBooking() == null || record.getBooking().getId() == null) {
            Booking booking = new Booking();
            booking.setId(bookingId);
            record.setBooking(booking);
        }
        model.addAttribute("medicalRecord", record);
        return "doctor/medical-record-form";
    }

    // Lưu thông tin hồ sơ bệnh án (thêm mới hoặc cập nhật)
    @PostMapping("/medical-record/save")
    public String saveMedicalRecord(@ModelAttribute("medicalRecord") MedicalRecord record) {
        // Lưu MedicalRecord
        medicalRecordService.save(record);
        // Quay về danh sách completed-appointments, bạn có thể sửa lại đường dẫn nếu cần
        return "redirect:/doctor/completed-appointments";
    }

    // Xem chi tiết hồ sơ bệnh án
    @GetMapping("/medical-record/{bookingId}/view")
    public String viewMedicalRecord(@PathVariable Long bookingId, Model model) {
        Optional<MedicalRecord> recordOpt = medicalRecordService.findByBookingId(bookingId);
        if (recordOpt.isPresent()) {
            model.addAttribute("medicalRecord", recordOpt.get());
        } else {
            model.addAttribute("medicalRecord", null);
            model.addAttribute("bookingId", bookingId);
        }
        return "doctor/medical-record-view";
    }

    // Các chức năng khác vẫn giữ nguyên
    @GetMapping("/assessments")
    public String assessments() {
        return "doctor/assessments";
    }

    @GetMapping("/resources")
    public String resources() {
        return "doctor/resources";
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "doctor/notifications";
    }

    @GetMapping("/profile")
    public String profile() {
        return "doctor/profile";
    }

    // Xử lý cập nhật trạng thái lịch hẹn
    @PostMapping("/appointments/update-status")
    public String updateStatus(@RequestParam("bookingId") Long bookingId,
                               @RequestParam("status") String status,
                               Principal principal) {
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/doctor/appointments";
    }
}
