package com.drug.drug.repository;

import com.drug.drug.entity.MedicalRecord;
import com.drug.drug.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByBookingId(Long bookingId);
    boolean existsByBookingId(Long bookingId);
}
