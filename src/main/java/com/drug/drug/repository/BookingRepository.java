package com.drug.drug.repository;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Tìm tất cả booking của một user
    List<Booking> findByUser(User user);
}
