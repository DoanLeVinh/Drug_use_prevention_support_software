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

    // --------- Kiểm tra tồn tại ----------
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    // --------- Đăng ký user mới -----------
    public User register(User user) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("member");
        }
        return userRepository.save(user);
    }

    // --------- Lấy user theo username/email/id -----------
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // --------- Lấy theo role (dùng cho phân trang/thống kê/hiển thị nhóm role) -----------
    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    // --------- Lấy tất cả user -----------
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --------- Cập nhật thông tin user (sửa user cho admin) -----------
    public User updateUser(User user) {
        // Chỉ update nếu tồn tại user
        Optional<User> exist = userRepository.findById(user.getId());
        if (exist.isPresent()) {
            return userRepository.save(user);
        }
        return null;
    }

    // --------- Xóa user theo id -----------
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // --------- Update role cho user -----------
    public User updateUserRole(Long id, String newRole) {
        User user = getUserById(id);
        if (user != null) {
            user.setRole(newRole);
            return userRepository.save(user);
        }
        return null;
    }

    // --------- Update password cho user -----------
    public User updatePassword(Long id, String newPassword) {
        User user = getUserById(id);
        if (user != null) {
            user.setPassword(newPassword);
            return userRepository.save(user);
        }
        return null;
    }

    // --------- Update username cho user -----------
    public User updateUsername(Long id, String newUsername) {
        // Không cho phép trùng username
        if (isUsernameTaken(newUsername)) return null;
        User user = getUserById(id);
        if (user != null) {
            user.setUsername(newUsername);
            return userRepository.save(user);
        }
        return null;
    }
}
