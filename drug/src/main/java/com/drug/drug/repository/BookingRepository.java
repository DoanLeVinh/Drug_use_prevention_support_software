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

    // Lấy tất cả bookings, join luôn với User (dành cho hiển thị giao diện tổng quát)
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.user ORDER BY b.bookingDate DESC, b.bookingTime DESC")
    List<Booking> findAllWithUser();

    List<Booking> findByUser(User user);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByConsultant(String consultant);

    List<Booking> findByConsultantAndBookingDate(String consultant, LocalDate bookingDate);

    List<Booking> findByStatus(String status);

    List<Booking> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);

    List<Booking> findByUserAndStatus(User user, String status);

    @Query("SELECT b FROM Booking b WHERE b.user.email = ?1")
    List<Booking> findByUserEmail(String email);

    // Bổ sung lọc theo consultant + trạng thái
    List<Booking> findByConsultantAndStatus(String consultant, String status);

    // Bổ sung lọc theo trạng thái và khoảng ngày
    List<Booking> findByStatusAndBookingDateBetween(String status, LocalDate startDate, LocalDate endDate);

   

}
