package com.drug.drug.repository;

import com.drug.drug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user bằng username
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    // Tìm user bằng email
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Tìm user bằng số điện thoại (sdt)
    Optional<User> findBySdt(String sdt);
    boolean existsBySdt(String sdt);

    // Lấy tất cả user theo vai trò
    List<User> findByRole(String role);

    // Các method findAll(), findById() đã được kế thừa mặc định, KHÔNG cần khai báo lại!
}
