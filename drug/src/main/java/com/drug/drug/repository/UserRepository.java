package com.drug.drug.repository;

import com.drug.drug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user bằng username (login bằng username)
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    // Tìm user bằng email (login bằng email)
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Tìm user bằng số điện thoại (nếu cần)
    Optional<User> findBySdt(String sdt);
    boolean existsBySdt(String sdt);

    // Tiện ích: Lấy trực tiếp User (nếu chắc chắn tồn tại)
    default User getByUsername(String username) {
        return findByUsername(username).orElse(null);
    }

    default User getByEmail(String email) {
        return findByEmail(email).orElse(null);
    }

    default User getBySdt(String sdt) {
        return findBySdt(sdt).orElse(null);
    }

    // Lấy tất cả người dùng có vai trò "member"
    List<User> findByRole(String role);

    // Lấy tất cả người dùng
    List<User> findAll();

    // Lấy người dùng theo ID
    Optional<User> findById(Long id);
    
}
