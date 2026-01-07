package com.example.sentiment.infrastructure.config;

import com.example.sentiment.adapter.controller.RestControllerPublisher;
import com.example.sentiment.adapter.file.FileReviewAdapter;
import com.example.sentiment.adapter.rest.RestReviewAdapter;
import com.example.sentiment.domain.port.ReviewFetcher;
import com.example.sentiment.domain.port.SentimentPublisher;
import com.example.sentiment.domain.service.SentimentAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {

    // === Переключение адаптеров: раскомментируйте нужный вариант ===

    @Bean
    public ReviewFetcher reviewFetcher() {
        return new FileReviewAdapter();      // ← Используется файл
        // return new RestReviewAdapter();   // ← Или REST
    }

    @Bean
    public SentimentPublisher sentimentPublisher() {
        return new RestControllerPublisher(); // ← Возврат через REST
        // return new LoggingPublisher();     // ← Или логирование
    }

    @Bean
    public SentimentAnalyzer sentimentAnalyzer(ReviewFetcher fetcher, SentimentPublisher publisher) {
        return new SentimentAnalyzer(fetcher, publisher);
    }
}