package com.example.horserace.websocket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class RaceWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try{
            session.sendMessage(new TextMessage("Race started"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}