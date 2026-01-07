package com.example.sentiment.domain.model;

public class Review {
    private final String text;

    public Review(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}