package com.drug.drug.repository;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Truy vấn tất cả booking và liên kết với người dùng
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.user ORDER BY b.bookingDate DESC, b.bookingTime DESC")
    List<Booking> findAllWithUser();

    // Lấy tất cả booking của một user
    List<Booking> findByUser(User user);

    // Lấy tất cả booking theo consultant và ngày đặt
    List<Booking> findByConsultantAndBookingDate(String consultant, LocalDate bookingDate);

    // Lấy tất cả booking của một user theo userId
    List<Booking> findByUserId(Long userId);

    // Lấy tất cả booking có trạng thái "Chờ xác nhận" hoặc trạng thái cụ thể
    List<Booking> findByStatus(String status);

    // Lấy tất cả booking trong một khoảng thời gian
    List<Booking> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);

    // Tìm tất cả các booking của một user với trạng thái cụ thể
    List<Booking> findByUserAndStatus(User user, String status);

    // Truy vấn booking theo email người dùng
    @Query("SELECT b FROM Booking b WHERE b.user.email = ?1")
    List<Booking> findByUserEmail(String email);

    
}
