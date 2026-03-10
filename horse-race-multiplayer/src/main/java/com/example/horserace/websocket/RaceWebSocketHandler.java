package com.example.horserace.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class RaceWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        sessions.add(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        sessions.remove(session);

    }

    public void broadcast(String message) {

        for (WebSocketSession session : sessions) {

            try {

                session.sendMessage(new TextMessage(message));

            } catch (Exception ignored) {
            }

        }

    }

}