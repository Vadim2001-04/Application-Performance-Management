package com.example.review.mongo;

import com.example.review.common.Review;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoReviewService {

    private final MongoReviewRepository repository;

    public MongoReviewService(MongoReviewRepository repository) {
        this.repository = repository;
    }

    public void save(Review review) {
        MongoReview doc = new MongoReview();
        doc.setText(review.getText());
        doc.setRating(review.getRating());
        doc.setProductId(review.getProductId());
        doc.setCreatedAt(review.getCreatedAt());
        repository.save(doc);
    }

    public void saveBatch(List<Review> reviews) {
        List<MongoReview> docs = reviews.stream().map(r -> {
            MongoReview d = new MongoReview();
            d.setText(r.getText());
            d.setRating(r.getRating());
            d.setProductId(r.getProductId());
            d.setCreatedAt(r.getCreatedAt());
            return d;
        }).collect(Collectors.toList());
        repository.saveAll(docs);
    }

    public Review findById(String id) {
        return repository.findById(id).map(d -> {
            Review r = new Review();
            r.setId(d.getId());
            r.setText(d.getText());
            r.setRating(d.getRating());
            r.setProductId(d.getProductId());
            r.setCreatedAt(d.getCreatedAt());
            return r;
        }).orElse(null);
    }

    public List<Review> findByProductIdAndDate(String productId, LocalDateTime after) {
        return repository.findByProductIdAndCreatedAtAfter(productId, after).stream().map(d -> {
            Review r = new Review();
            r.setId(d.getId());
            r.setText(d.getText());
            r.setRating(d.getRating());
            r.setProductId(d.getProductId());
            r.setCreatedAt(d.getCreatedAt());
            return r;
        }).collect(Collectors.toList());
    }
}