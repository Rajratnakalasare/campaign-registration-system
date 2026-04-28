package com.company.campaign.survey.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateQuestionRequest {

    @NotBlank
    private String questionText;

    @NotBlank
    private String questionType; // TEXT, RADIO, CHECKBOX, DATE, NUMBER

    @NotNull
    private Boolean mandatory;

    // Only for RADIO / CHECKBOX
    private List<String> options;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
