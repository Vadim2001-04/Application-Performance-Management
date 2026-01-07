package com.example.sentiment.domain.service;

import com.example.sentiment.domain.model.Review;
import com.example.sentiment.domain.model.Sentiment;
import com.example.sentiment.domain.port.ReviewFetcher;
import com.example.sentiment.domain.port.SentimentPublisher;

public class SentimentAnalyzer {

    private final ReviewFetcher reviewFetcher;
    private final SentimentPublisher sentimentPublisher;

    public SentimentAnalyzer(ReviewFetcher reviewFetcher, SentimentPublisher sentimentPublisher) {
        this.reviewFetcher = reviewFetcher;
        this.sentimentPublisher = sentimentPublisher;
    }

    public void analyze() {
        Review review = reviewFetcher.fetchReview();
        Sentiment sentiment = determineSentiment(review.getText());
        sentimentPublisher.publish(sentiment);
    }

    private Sentiment determineSentiment(String text) {
        if (text == null || text.isEmpty()) return Sentiment.NEGATIVE;
        String lower = text.toLowerCase();
        if (lower.contains("хорош") || lower.contains("отличн") || lower.contains("лучш")) {
            return Sentiment.POSITIVE;
        }
        return Sentiment.NEGATIVE;
    }
}