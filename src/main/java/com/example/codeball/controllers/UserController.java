package com.example.codeball.controllers;

import com.example.codeball.model.RoleUser;
import com.example.codeball.model.User;
import com.example.codeball.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowCredentials = "true", maxAge = 3600L)
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/me")
    public User getCurrentUser() {
        return new User("bla", "bla", "bla", RoleUser.ROLE_USER);
    }

    @GetMapping("/api/user")
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/api/user")
    public ResponseEntity createUser (@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}")
    public User getUserById (@PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user don't exist"));
    }

    @DeleteMapping("/api/user/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable int id) {
        user.setId(id);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }


}
