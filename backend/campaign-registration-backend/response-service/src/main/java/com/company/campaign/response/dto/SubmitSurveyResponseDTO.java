package com.company.campaign.response.dto;

import java.time.LocalDateTime;

public class SubmitSurveyResponseDTO {
    private String message;
    private Long responseId;
    private LocalDateTime submittedAt;

    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Long getResponseId() {
        return responseId;
    }
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

}
