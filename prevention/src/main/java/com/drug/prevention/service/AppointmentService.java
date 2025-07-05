package com.drug.prevention.service;

import com.drug.prevention.entity.Appointment;
import com.drug.prevention.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = appointmentRepository.findById(id).orElseThrow();
        existing.setAppointmentTime(updated.getAppointmentTime());
        existing.setStatus(updated.getStatus());
        return appointmentRepository.save(existing);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
