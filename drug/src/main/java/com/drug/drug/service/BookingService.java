package com.drug.drug.service;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.User;
import com.drug.drug.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllWithUser();
    }

     public Booking saveBooking(Booking booking) {
        // Kiểm tra xem booking đã có id chưa
        if (booking.getId() != null) {
            // Nếu có id thì kiểm tra xem có tồn tại trong DB không
            return bookingRepository.findById(booking.getId())
                    .map(existingBooking -> {
                        // Cập nhật thông tin
                        existingBooking.setBookingDate(booking.getBookingDate());
                        existingBooking.setBookingTime(booking.getBookingTime());
                        existingBooking.setConsultant(booking.getConsultant());
                        existingBooking.setConsultationType(booking.getConsultationType());
                        existingBooking.setEmail(booking.getEmail());
                        existingBooking.setFullname(booking.getFullname());
                        existingBooking.setNote(booking.getNote());
                        existingBooking.setPhone(booking.getPhone());
                        existingBooking.setService(booking.getService());
                        existingBooking.setStatus(booking.getStatus());
                        existingBooking.setUser(booking.getUser());
                        return bookingRepository.save(existingBooking);
                    })
                    .orElseGet(() -> {
                        // Nếu không tồn tại thì tạo mới
                        return bookingRepository.save(booking);
                    });
        }
        // Nếu không có id thì tạo mới
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking getBookingById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    public Booking updateBookingStatus(Long id, String status) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    
}