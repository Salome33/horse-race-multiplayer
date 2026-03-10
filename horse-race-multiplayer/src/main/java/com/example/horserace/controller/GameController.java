package com.example.horserace.controller;

import com.example.horserace.engine.RaceEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    private final RaceEngine raceEngine;

    public GameController(RaceEngine raceEngine) {
        this.raceEngine = raceEngine;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/game/start")
    @ResponseBody
    public String startRace() {
        raceEngine.startRace();
        return "Race started";
    }
}