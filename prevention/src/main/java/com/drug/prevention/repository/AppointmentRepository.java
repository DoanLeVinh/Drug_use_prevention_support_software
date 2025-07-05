package com.drug.prevention.repository;

import com.drug.prevention.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserName(String userName);
    List<Appointment> findByConsultantName(String consultantName);
}
