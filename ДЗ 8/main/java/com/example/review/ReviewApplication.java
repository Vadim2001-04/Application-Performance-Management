package com.example.review;

import com.example.review.common.Review;
import com.example.review.jpa.JpaReviewService;
import com.example.review.mongo.MongoReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RestController
public class ReviewApplication {

    @Autowired(required = false)
    private JpaReviewService jpaService;

    @Autowired(required = false)
    private MongoReviewService mongoService;

    // Для тестов — генерация одного отзыва
    private Review createSampleReview() {
        Review r = new Review("Отличный товар!", 5, "prod-123");
        r.setId(UUID.randomUUID().toString());
        return r;
    }

    @PostMapping("/api/review")
    public String saveReview() {
        Review review = createSampleReview();
        if (jpaService != null) {
            jpaService.save(review);
            return "Saved to PostgreSQL";
        } else if (mongoService != null) {
            mongoService.save(review);
            return "Saved to MongoDB";
        }
        return "No DB configured";
    }

    @PostMapping("/api/review/batch")
    public String saveBatch() {
        Review review = createSampleReview();
        if (jpaService != null) {
            jpaService.saveBatch(java.util.stream.Stream.generate(() -> createSampleReview())
                    .limit(1000).toList());
            return "1000 saved to PostgreSQL";
        } else if (mongoService != null) {
            mongoService.saveBatch(java.util.stream.Stream.generate(() -> createSampleReview())
                    .limit(1000).toList());
            return "1000 saved to MongoDB";
        }
        return "No DB configured";
    }

    @GetMapping("/api/review/test")
    public List<Review> testQuery() {
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        if (jpaService != null) {
            return jpaService.findByProductIdAndDate("prod-123", weekAgo);
        } else if (mongoService != null) {
            return mongoService.findByProductIdAndDate("prod-123", weekAgo);
        }
        return List.of();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReviewApplication.class, args);
    }
}