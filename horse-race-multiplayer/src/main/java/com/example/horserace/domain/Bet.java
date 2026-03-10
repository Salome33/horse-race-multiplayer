package com.example.horserace.domain;

public class Bet {

    private Long userId;
    private int amount;
    private String horse;

    public Bet() {
    }

    public Bet(Long userId, int amount, String horse) {
        this.userId = userId;
        this.amount = amount;
        this.horse = horse;
    }

    public Long getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public String getHorse() {
        return horse;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setHorse(String horse) {
        this.horse = horse;
    }
}