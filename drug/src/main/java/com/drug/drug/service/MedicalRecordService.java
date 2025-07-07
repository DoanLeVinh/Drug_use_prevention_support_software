package com.drug.drug.service;

import com.drug.drug.entity.MedicalRecord;
import com.drug.drug.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Optional<MedicalRecord> findByBookingId(Long bookingId) {
        return medicalRecordRepository.findByBookingId(bookingId);
    }

    public MedicalRecord save(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }

    public Optional<MedicalRecord> findById(Long id) {
        return medicalRecordRepository.findById(id);
    }
}
