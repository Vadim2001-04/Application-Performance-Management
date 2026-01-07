package com.example.sentiment;

import com.example.sentiment.adapter.controller.RestControllerPublisher;
import com.example.sentiment.domain.service.SentimentAnalyzer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SentimentApplication {

    private final SentimentAnalyzer analyzer;
    private final RestControllerPublisher publisher;

    public SentimentApplication(SentimentAnalyzer analyzer, RestControllerPublisher publisher) {
        this.analyzer = analyzer;
        this.publisher = publisher;
    }

    @GetMapping("/api/analyze/file")
    public String analyzeFromFile() {
        analyzer.analyze();
        return publisher.getResult().name();
    }

    @GetMapping("/api/analyze/rest")
    public String analyzeFromRest() {
        analyzer.analyze();
        return publisher.getResult().name();
    }

    public static void main(String[] args) {
        SpringApplication.run(SentimentApplication.class, args);
    }
}