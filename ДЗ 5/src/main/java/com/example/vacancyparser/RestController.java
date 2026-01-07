package com.example.vacancyparser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class RestController {

    private final ParsingService parsingService;
    private volatile CompletableFuture<String> currentTask = null;

    public RestController(ParsingService parsingService) {
        this.parsingService = parsingService;
    }

    @GetMapping("/api/start")
    public String startParsing() {
        if (currentTask == null || currentTask.isDone()) {
            currentTask = parsingService.startParsing();
        }
        return "Parsing started";
    }

    @GetMapping("/api/status")
    public String getStatus() {
        if (currentTask == null) {
            return "{\"status\":\"idle\"}";
        }
        if (!currentTask.isDone()) {
            return "{\"status\":\"processing\"}";
        }
        try {
            String result = currentTask.get();
            currentTask = null;
            return "{\"status\":\"completed\",\"data\":" + result + "}";
        } catch (Exception e) {
            currentTask = null;
            return "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
        }
    }
}