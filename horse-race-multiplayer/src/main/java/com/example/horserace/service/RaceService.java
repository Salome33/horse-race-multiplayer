package com.example.horserace.service;

import org.springframework.stereotype.Service;

@Service
public class RaceService {

    public int calculatePrize(int bet){
        return bet * 5;
    }

}