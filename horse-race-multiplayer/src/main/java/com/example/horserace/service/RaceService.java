package com.example.horserace.service;

import com.example.horserace.domain.Bet;
import com.example.horserace.domain.RaceRoom;
import com.example.horserace.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RaceService {

    private final RaceRoom raceRoom = new RaceRoom();

    private final List<Bet> bets = new ArrayList<>();

    public synchronized boolean joinRoom(User user) {

        return raceRoom.addPlayer(user);

    }

    public RaceRoom getRaceRoom() {
        return raceRoom;
    }

    public synchronized Bet placeBet(Bet bet) {

        bets.add(bet);

        return bet;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void clearBets() {

        bets.clear();

    }

}