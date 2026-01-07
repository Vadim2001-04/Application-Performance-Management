package com.example.sentiment.adapter.rest;

import com.example.sentiment.domain.model.Review;
import com.example.sentiment.domain.port.ReviewFetcher;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestReviewAdapter implements ReviewFetcher {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Review fetchReview() {
        String text = restTemplate.getForObject("http://localhost:8080/api/review/mock", String.class);
        return new Review(text);
    }
}