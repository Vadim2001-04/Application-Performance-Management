package com.example.vacancyservice;

import java.io.Serializable;

public class Vacancy implements Serializable {
    private Long id;
    private String title;
    private String company;
    private String description;
    private Double salary;

    // Конструкторы
    public Vacancy() {}

    public Vacancy(Long id, String title, String company, String description, Double salary) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.salary = salary;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}