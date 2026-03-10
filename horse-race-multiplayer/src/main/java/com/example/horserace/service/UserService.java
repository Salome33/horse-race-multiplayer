package com.example.horserace.service;

import com.example.horserace.domain.User;
import com.example.horserace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username) {
        Optional<User> existing = userRepository.findByUsername(username);

        if (existing.isPresent()) {
            return existing.get();
        }

        User user = new User();
        user.setUsername(username);
        user.setPoints(1000);

        return userRepository.save(user);
    }

    public User buyPoints(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPoints(user.getPoints() + 1000);

        return userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User discountPoints(Long userId, int points) {
        User user = getUser(userId);

        if (user.getPoints() < points) {
            throw new RuntimeException("Puntos insuficientes");
        }

        user.setPoints(user.getPoints() - points);
        return userRepository.save(user);
    }

    public User addPoints(Long userId, int points) {
        User user = getUser(userId);
        user.setPoints(user.getPoints() + points);
        return userRepository.save(user);
    }
}