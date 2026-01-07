package com.example.sentiment.adapter.file;

import com.example.sentiment.domain.model.Review;
import com.example.sentiment.domain.port.ReviewFetcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileReviewAdapter implements ReviewFetcher {

    @Override
    public Review fetchReview() {
        try {
            String content = Files.readString(Paths.get("src/main/resources/reviews.txt"));
            return new Review(content.trim());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл", e);
        }
    }
}