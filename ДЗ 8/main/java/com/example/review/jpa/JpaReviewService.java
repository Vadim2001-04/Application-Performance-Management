package com.example.review.jpa;

import com.example.review.common.Review;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaReviewService {

    private final JpaReviewRepository repository;

    public JpaReviewService(JpaReviewRepository repository) {
        this.repository = repository;
    }

    public void save(Review review) {
        JpaReview entity = new JpaReview();
        entity.setText(review.getText());
        entity.setRating(review.getRating());
        entity.setProductId(review.getProductId());
        entity.setCreatedAt(review.getCreatedAt());
        repository.save(entity);
    }

    public void saveBatch(List<Review> reviews) {
        List<JpaReview> entities = reviews.stream().map(r -> {
            JpaReview e = new JpaReview();
            e.setText(r.getText());
            e.setRating(r.getRating());
            e.setProductId(r.getProductId());
            e.setCreatedAt(r.getCreatedAt());
            return e;
        }).collect(Collectors.toList());
        repository.saveAll(entities);
    }

    public Review findById(Long id) {
        return repository.findById(id).map(e -> {
            Review r = new Review();
            r.setId(e.getId().toString());
            r.setText(e.getText());
            r.setRating(e.getRating());
            r.setProductId(e.getProductId());
            r.setCreatedAt(e.getCreatedAt());
            return r;
        }).orElse(null);
    }

    public List<Review> findByProductIdAndDate(String productId, LocalDateTime after) {
        return repository.findByProductIdAndCreatedAtAfter(productId, after).stream().map(e -> {
            Review r = new Review();
            r.setId(e.getId().toString());
            r.setText(e.getText());
            r.setRating(e.getRating());
            r.setProductId(e.getProductId());
            r.setCreatedAt(e.getCreatedAt());
            return r;
        }).collect(Collectors.toList());
    }
}