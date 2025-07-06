package com.drug.drug.service;

import com.drug.drug.entity.User;
import com.drug.drug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // Phương thức mới để lấy user theo ID
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Cập nhật user
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Xóa user theo ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Lấy tất cả user có vai trò là "member"
    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    // Cập nhật vai trò của người dùng
    public User updateUserRole(Long id, String newRole) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setRole(newRole);
            return userRepository.save(user);
        }
        return null;
    }

    // Lấy tất cả user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
}

}
