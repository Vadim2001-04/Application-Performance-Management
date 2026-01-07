package com.example.sentiment.infrastructure.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockReviewController {

    @GetMapping("/api/review/mock")
    public String getMockReview() {
        return "Этот продукт просто отличный! Лучший выбор за эти деньги.";
    }
}