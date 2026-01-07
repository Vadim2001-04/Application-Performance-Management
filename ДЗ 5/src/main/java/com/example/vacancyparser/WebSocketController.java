package com.example.vacancyparser;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CompletableFuture;

public class WebSocketController extends TextWebSocketHandler {

    private final ParsingService parsingService = new ParsingService();
    private volatile CompletableFuture<String> currentTask = null;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if ("start".equals(message.getPayload())) {
            if (currentTask == null || currentTask.isDone()) {
                currentTask = parsingService.startParsing();
                currentTask.thenAccept(result -> {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(new TextMessage(
                                    "{\"status\":\"completed\",\"data\":" + result + "}"
                            ));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).exceptionally(ex -> {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(new TextMessage(
                                    "{\"status\":\"error\",\"message\":\"" + ex.getMessage() + "\"}"
                            ));
                        }
                    } catch (Exception ignored) {}
                    return null;
                });
            }
        }
    }
}