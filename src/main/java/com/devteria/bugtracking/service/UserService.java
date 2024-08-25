package com.devteria.bugtracking.service;

import com.devteria.bugtracking.dto.ErrorCode;
import com.devteria.bugtracking.entity.User;
import com.devteria.bugtracking.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    UserRepository userRepository;


    public User createUser(User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new Exception(ErrorCode.USER_EXISTED.getMessage());
            }
            user.setPassword(user.getPassword());
            return userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            throw new RuntimeException("Error creating user", e);
        }
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}