package com.drug.drug.service;

import com.drug.drug.entity.User;
import com.drug.drug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public User register(User user) {
        // Gán role member
        user.setRole("member");
        return userRepository.save(user);
    }
}
