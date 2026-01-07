package com.example.sentiment.domain.port;

import com.example.sentiment.domain.model.Review;

public interface ReviewFetcher {
    Review fetchReview();
}