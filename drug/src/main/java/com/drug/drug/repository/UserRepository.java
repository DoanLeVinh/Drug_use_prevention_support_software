// filepath: src/main/java/com/drug/drug/repository/UserRepository.java
package com.drug.drug.repository;

import com.drug.drug.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}