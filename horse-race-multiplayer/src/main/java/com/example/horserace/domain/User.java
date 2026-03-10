package com.example.horserace.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int points = 1000;

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.points = 1000;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}