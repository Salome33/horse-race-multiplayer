package com.example.horserace.domain;

import java.util.ArrayList;
import java.util.List;

public class RaceRoom {

    private final List<User> players = new ArrayList<>();

    private boolean raceStarted = false;

    public synchronized boolean addPlayer(User user) {

        if (players.size() >= 4) {
            return false;
        }

        players.add(user);

        return true;
    }

    public List<User> getPlayers() {
        return players;
    }

    public boolean isRaceStarted() {
        return raceStarted;
    }

    public void setRaceStarted(boolean raceStarted) {
        this.raceStarted = raceStarted;
    }

    public boolean isFull() {
        return players.size() == 4;
    }

}