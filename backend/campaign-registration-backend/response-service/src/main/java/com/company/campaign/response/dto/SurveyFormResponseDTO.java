package com.company.campaign.response.dto;

import java.time.LocalDate;
import java.util.List;


public class SurveyFormResponseDTO {
    
    private long surveyId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<QuestionDTO> questions;

    
    public long getSurveyId() {
        return surveyId;
    }
    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
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
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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
    public List<QuestionDTO> getQuestions() {
        return questions;
    }
    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    } 

    
}
