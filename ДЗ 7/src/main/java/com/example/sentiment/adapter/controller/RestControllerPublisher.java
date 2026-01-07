package com.example.sentiment.adapter.controller;

import com.example.sentiment.domain.model.Sentiment;
import com.example.sentiment.domain.port.SentimentPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class RestControllerPublisher implements SentimentPublisher {

    private volatile Sentiment lastResult = null;

    @Override
    public void publish(Sentiment sentiment) {
        this.lastResult = sentiment;
    }

    public Sentiment getResult() {
        return lastResult;
    }
}