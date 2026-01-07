package com.example.review.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaReviewRepository extends JpaRepository<JpaReview, Long> {
    List<JpaReview> findByProductId(String productId);
    List<JpaReview> findByProductIdAndCreatedAtAfter(String productId, LocalDateTime after);
}