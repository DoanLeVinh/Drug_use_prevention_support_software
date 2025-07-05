package com.drug.drug.service;

import com.drug.drug.entity.User;
import com.drug.drug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Kiểm tra username đã tồn tại chưa
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    // Đăng ký user mới
    public User register(User user) {
        user.setRole("member"); // Gán role mặc định là member
        return userRepository.save(user);
    }

    // Lấy user theo username (dùng cho login, lấy user id,...)
    public User findByUsername(String username) {
        // Trả về User hoặc null nếu không tìm thấy
        return userRepository.findByUsername(username).orElse(null);
    }

    // Lấy user theo ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Cập nhật user
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Xóa user theo ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
