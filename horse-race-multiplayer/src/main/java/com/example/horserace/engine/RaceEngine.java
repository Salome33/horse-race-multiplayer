package com.example.horserace.engine;

import com.example.horserace.domain.Bet;
import com.example.horserace.service.RaceService;
import com.example.horserace.service.UserService;
import com.example.horserace.websocket.RaceWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RaceEngine {

    private final RaceWebSocketHandler socketHandler;
    private final RaceService raceService;
    private final UserService userService;

    public RaceEngine(RaceWebSocketHandler socketHandler,
                      RaceService raceService,
                      UserService userService) {
        this.socketHandler = socketHandler;
        this.raceService = raceService;
        this.userService = userService;
    }

    public void startRace() {

        new Thread(() -> {

            int horse1 = 0;
            int horse2 = 0;
            int horse3 = 0;
            int horse4 = 0;

            Random random = new Random();
            boolean finished = false;

            String winner = null;

            while (!finished) {

                horse1 += random.nextInt(25);
                horse2 += random.nextInt(25);
                horse3 += random.nextInt(25);
                horse4 += random.nextInt(25);

                if (horse1 >= 700) {
                    winner = "OROS";
                    finished = true;
                }

                if (horse2 >= 700) {
                    winner = "COPAS";
                    finished = true;
                }

                if (horse3 >= 700) {
                    winner = "ESPADAS";
                    finished = true;
                }

                if (horse4 >= 700) {
                    winner = "BASTOS";
                    finished = true;
                }

                String message;

                if (finished) {

                    payWinner(winner);

                    message = String.format(
                            "{\"h1\":%d,\"h2\":%d,\"h3\":%d,\"h4\":%d,\"winner\":\"%s\"}",
                            horse1, horse2, horse3, horse4, winner
                    );

                    raceService.clearBets();

                } else {

                    message = String.format(
                            "{\"h1\":%d,\"h2\":%d,\"h3\":%d,\"h4\":%d}",
                            horse1, horse2, horse3, horse4
                    );

                }

                socketHandler.broadcast(message);

                try {
                    Thread.sleep(300);
                } catch (Exception ignored) {
                }

            }

        }).start();

    }

    private void payWinner(String winnerHorse) {

        List<Bet> bets = raceService.getBets();

        for (Bet bet : bets) {

            if (bet.getHorse().equals(winnerHorse)) {

                int prize = bet.getAmount() * 5;

                userService.addPoints(bet.getUserId(), prize);

            }

        }

    }

}