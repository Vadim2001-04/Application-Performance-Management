package com.example.sentiment.domain.port;

import com.example.sentiment.domain.model.Sentiment;

public interface SentimentPublisher {
    void publish(Sentiment sentiment);
}