package com.example.review.config;

import com.example.review.jpa.JpaReviewService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
public class JpaConfig {
    @Bean
    public Object reviewService(com.example.review.jpa.JpaReviewRepository repo) {
        return new JpaReviewService(repo);
    }
}