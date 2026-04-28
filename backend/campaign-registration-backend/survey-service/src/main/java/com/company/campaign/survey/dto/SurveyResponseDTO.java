package com.company.campaign.survey.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class SurveyResponseDTO {
    
    private Long id;
    private String title;
    private String description;
    private LocalDate starDate;
    private LocalDate endDate;
    private String status;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getStarDate() {
        return starDate;
    }
    public void setStarDate(LocalDate starDate) {
        this.starDate = starDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
}
