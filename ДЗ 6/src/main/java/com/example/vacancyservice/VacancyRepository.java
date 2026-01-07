package com.example.vacancyservice;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VacancyRepository {

    private final Map<Long, Vacancy> storage = new HashMap<>();

    public VacancyRepository() {
        // Инициализация тестовых данных
        storage.put(1L, new Vacancy(1L, "Java Developer", "TechCorp", "Разработка backend-сервисов", 150000.0));
        storage.put(2L, new Vacancy(2L, "Frontend Engineer", "WebLabs", "Разработка интерфейсов", 130000.0));
    }

    public Vacancy findById(Long id) {
        try {
            // Имитация задержки БД/API — 200 мс
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return storage.get(id);
    }

    public void update(Vacancy vacancy) {
        storage.put(vacancy.getId(), vacancy);
    }
}