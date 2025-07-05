package com.drug.drug.controller;

import com.drug.drug.entity.Booking;
import com.drug.drug.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookingApiController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/available-hours")
    public ResponseEntity<List<String>> getAvailableHours(
            @RequestParam String doctor,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        // Lấy tất cả booking đã có cho bác sĩ và ngày này
        List<Booking> existingBookings = bookingRepository
                .findByConsultantAndBookingDate(doctor, date);
        
        // Tạo danh sách giờ đã đặt
        Set<String> bookedHours = existingBookings.stream()
                .map(Booking::getBookingTime)
                .collect(Collectors.toSet());
        
        // Tạo danh sách giờ làm việc (8:00 - 17:00, mỗi 30 phút)
        List<String> allSlots = new ArrayList<>();
        for (int hour = 8; hour < 17; hour++) {
            allSlots.add(String.format("%02d:00", hour));
            allSlots.add(String.format("%02d:30", hour));
        }
        
        // Lọc ra các giờ còn trống
        List<String> availableHours = allSlots.stream()
                .filter(slot -> !bookedHours.contains(slot))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(availableHours);
    }
}