package com.example.vacancyservice;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vacancies")
public class VacancyController {

    private final VacancyRepository repository;

    public VacancyController(VacancyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    @Cacheable(value = "vacancies", key = "#id")
    public Vacancy getVacancy(@PathVariable Long id) {
        System.out.println("Запрос к источнику для ID: " + id); // Для логирования промахов
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "vacancies", key = "#id")
    public Vacancy updateVacancy(@PathVariable Long id, @RequestBody Vacancy updated) {
        updated.setId(id);
        repository.update(updated);
        return updated;
    }
}