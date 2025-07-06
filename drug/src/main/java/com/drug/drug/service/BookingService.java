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

    // Lấy tất cả các booking, bao gồm thông tin người dùng
    public List<Booking> getAllBookings() {
        return bookingRepository.findAllWithUser();
    }

    // Lưu booking mới hoặc cập nhật booking đã có
    public Booking saveBooking(Booking booking) {
        // Nếu booking đã có id thì kiểm tra trong DB, nếu có thì cập nhật
        if (booking.getId() != null) {
            return bookingRepository.findById(booking.getId())
                    .map(existingBooking -> {
                        // Cập nhật thông tin của booking
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

    // Lấy tất cả bookings của một user
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    // Lấy tất cả bookings của user thông qua userId
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // Lấy thông tin booking theo ID
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Cập nhật trạng thái của một booking
    public Booking updateBookingStatus(Long id, String status) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }

    // Xóa booking theo ID
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findByUserId(Long userId) {
    return bookingRepository.findByUserId(userId);
}

}
