package com.drug.drug.service;

import com.drug.drug.entity.Booking;
import com.drug.drug.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public void save(Booking booking) {
        bookingRepository.save(booking);
    }
}
