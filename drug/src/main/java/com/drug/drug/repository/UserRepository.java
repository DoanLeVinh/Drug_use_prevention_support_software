package com.drug.drug.repository;

import com.drug.drug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm user bằng username
    Optional<User> findByUsername(String username);

    // Kiểm tra username đã tồn tại chưa
    boolean existsByUsername(String username);

    // Tìm user bằng email
    Optional<User> findByEmail(String email);

    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);

    // Tìm user bằng số điện thoại
    Optional<User> findBySdt(String sdt);

    // Kiểm tra số điện thoại đã tồn tại chưa
    boolean existsBySdt(String sdt);
}
