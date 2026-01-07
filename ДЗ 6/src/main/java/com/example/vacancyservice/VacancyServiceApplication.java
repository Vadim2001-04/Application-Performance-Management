package com.example.vacancyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VacancyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VacancyServiceApplication.class, args);
    }
}