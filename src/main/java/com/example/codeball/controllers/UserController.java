package com.example.codeball.controllers;

import com.example.codeball.enums.Role;
import com.example.codeball.models.User;
import com.example.codeball.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600L)
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/me")
    public User getCurrentUser() {
        return new User("bla", "bla", 6, "bla", Role.ROLE_ADMIN);
    }

    @GetMapping("/api/user")
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/api/user")
    public ResponseEntity createUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/user/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user doesn't exist"));
    }

    @DeleteMapping("/api/user/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userRepository.deleteById(userId);
    }

    @PutMapping("/api/user/{userId}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable int userId) {
        user.setId(userId);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }


}
