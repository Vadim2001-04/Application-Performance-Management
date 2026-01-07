package com.example.review.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MongoReviewRepository extends MongoRepository<MongoReview, String> {
    List<MongoReview> findByProductId(String productId);
    List<MongoReview> findByProductIdAndCreatedAtAfter(String productId, LocalDateTime after);
}