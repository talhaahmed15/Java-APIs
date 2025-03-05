package com.petproject.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@Service
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    @GetMapping
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    public ResponseEntity<Map<String, Object>> signup(User user) {
        Optional<User> userByEmail = userRepo.findUserByEmail(user.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (userByEmail.isPresent()) {
            response.put("status", "error");
            response.put("statusCode", HttpStatus.CONFLICT.value());
            response.put("message", "Email already taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        response.put("status", "success");
        response.put("statusCode", HttpStatus.CREATED.value());
        response.put("message", "User registered successfully");
        response.put("data", Map.of("email", user.getEmail(), "name", user.getName())); // Exclude password in response

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Map<String, Object>> login(String email, String password) {
        Optional<User> userByEmail = userRepo.findUserByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (userByEmail.isEmpty()) {
            response.put("status", "error");
            response.put("statusCode", HttpStatus.UNAUTHORIZED.value());
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userByEmail.get();

        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            response.put("status", "error");
            response.put("statusCode", HttpStatus.UNAUTHORIZED.value());
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", "Login successful");
        response.put("data", Map.of("email", user.getEmail(), "name", user.getName())); // Exclude password

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

