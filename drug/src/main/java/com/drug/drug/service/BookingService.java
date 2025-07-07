package com.drug.drug.service;

import com.drug.drug.entity.Booking;
import com.drug.drug.entity.User;
import com.drug.drug.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        if (booking.getId() != null) {
            return bookingRepository.findById(booking.getId())
                    .map(existingBooking -> {
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
                    .orElseGet(() -> bookingRepository.save(booking));
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // Lấy booking theo consultant (username doctor)
    public List<Booking> getBookingsByConsultant(String consultant) {
        return bookingRepository.findByConsultant(consultant);
    }

    // Lấy booking theo consultant và ngày đặt
    public List<Booking> getBookingsByConsultantAndBookingDate(String consultant, LocalDate bookingDate) {
        return bookingRepository.findByConsultantAndBookingDate(consultant, bookingDate);
    }

    // Lấy booking theo trạng thái
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

    // Lấy booking theo khoảng thời gian
    public List<Booking> getBookingsByDateRange(LocalDate start, LocalDate end) {
        return bookingRepository.findByBookingDateBetween(start, end);
    }

    // Lấy booking theo user và trạng thái
    public List<Booking> getBookingsByUserAndStatus(User user, String status) {
        return bookingRepository.findByUserAndStatus(user, status);
    }

    // Lấy booking theo email user
    public List<Booking> getBookingsByUserEmail(String email) {
        return bookingRepository.findByUserEmail(email);
    }

    // Lấy booking theo consultant và trạng thái
    public List<Booking> getBookingsByConsultantAndStatus(String consultant, String status) {
        return bookingRepository.findByConsultantAndStatus(consultant, status);
    }

    // Lấy booking theo trạng thái và khoảng ngày
    public List<Booking> getBookingsByStatusAndDateRange(String status, LocalDate start, LocalDate end) {
        return bookingRepository.findByStatusAndBookingDateBetween(status, start, end);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
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

    // Giữ lại hàm findByUserId cũ (để không ảnh hưởng code trước)
    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getCompletedBookingsByConsultant(String consultantUsername) {
    return bookingRepository.findByConsultantAndStatus(consultantUsername, "Hoàn thành");
}

}
