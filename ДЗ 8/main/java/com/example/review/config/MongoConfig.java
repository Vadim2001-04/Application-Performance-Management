package com.example.review.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongo")
public class MongoConfig {
    @Bean
    public Object reviewService(com.example.review.mongo.MongoReviewRepository repo) {
        return new com.example.review.mongo.MongoReviewService(repo);
    }
}