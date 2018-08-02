package com.example.codeball.models;

import com.example.codeball.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    @Autowired
    UserRepository userRepository;

    public Integer getCurrentUserId() {
        return 1;
    }

    public User getCurrentUser() {
        return userRepository.findById(1).orElseThrow(() -> new RuntimeException("User doesn't exist"));
    }
}
