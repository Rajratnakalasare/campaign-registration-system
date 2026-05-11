package com.company.campaign.response.controller;

import com.company.campaign.response.dto.SubmitSurveyRequestDTO;
import com.company.campaign.response.dto.SubmitSurveyResponseDTO;
import com.company.campaign.response.dto.SurveyFormResponseDTO;
import com.company.campaign.response.service.PublicSurveyService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicSurveyController {

    private final PublicSurveyService publicSurveyService;

    public PublicSurveyController(PublicSurveyService publicSurveyService) {
        this.publicSurveyService = publicSurveyService;
    }

    @GetMapping("/surveys/{surveyId}")
    public SurveyFormResponseDTO getSurveyForm(@PathVariable("surveyId") Long surveyId) {
        return publicSurveyService.getSurveyForm(surveyId);
    }

    @PostMapping("/surveys/{surveyId}/responses")
    @ResponseStatus(HttpStatus.CREATED)
    public SubmitSurveyResponseDTO submitSurvey(
            @PathVariable("surveyId") Long surveyId,
            @Valid @RequestBody SubmitSurveyRequestDTO request) {

        return publicSurveyService.submitSurvey(surveyId, request);
    }
}