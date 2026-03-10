package com.example.horserace.controller;

import com.example.horserace.domain.User;
import com.example.horserace.dto.RegisterRequest;
import com.example.horserace.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request.username);
    }

    @PostMapping("/buy-points")
    public User buyPoints(@RequestParam Long userId) {
        return userService.buyPoints(userId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}