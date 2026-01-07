package com.example.sentiment.adapter.logging;

import com.example.sentiment.domain.model.Sentiment;
import com.example.sentiment.domain.port.SentimentPublisher;
import org.springframework.stereotype.Component;

@Component
public class LoggingPublisher implements SentimentPublisher {

    @Override
    public void publish(Sentiment sentiment) {
        System.out.println("[LOG] Результат анализа тональности: " + sentiment);
    }
}