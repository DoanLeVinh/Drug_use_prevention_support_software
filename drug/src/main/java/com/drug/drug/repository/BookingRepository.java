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
    
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.user ORDER BY b.bookingDate DESC, b.bookingTime DESC")
    List<Booking> findAllWithUser();
    
    List<Booking> findByUser(User user);
    
    List<Booking> findByConsultantAndBookingDate(String consultant, LocalDate bookingDate);
    
    List<Booking> findByUserId(Long userId);
}