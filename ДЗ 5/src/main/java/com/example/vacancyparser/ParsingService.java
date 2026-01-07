package com.example.vacancyparser;

import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class ParsingService {

    private final Random random = new Random();

    public CompletableFuture<String> startParsing() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Имитация парсинга: 2–5 секунд
                int delay = 2000 + random.nextInt(3000);
                Thread.sleep(delay);
                return """
                    [
                      {"title": "Java Developer", "company": "TechCorp", "salary": "150000 RUB"},
                      {"title": "Frontend Engineer", "company": "WebLabs", "salary": "130000 RUB"}
                    ]
                    """;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Parsing interrupted", e);
            }
        });
    }
}