package com.company.campaign.survey.controller;

import com.company.campaign.survey.dto.CreateQuestionRequest;
import com.company.campaign.survey.dto.CreateSurveyRequest;
import com.company.campaign.survey.dto.QuestionResponseDTO;
import com.company.campaign.survey.dto.SurveyResponseDTO;
import com.company.campaign.survey.service.SurveyService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SurveyResponseDTO createSurvey(@Valid @RequestBody CreateSurveyRequest request){
        return surveyService.createSurvey(request);
    }

    @GetMapping
    public List<SurveyResponseDTO> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @GetMapping("/{id}")
    public SurveyResponseDTO getSurveyById(@PathVariable Long id){
        return surveyService.getSurveyById(id);
    }

    @PutMapping("/{id}/launch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void launchSurvey(@PathVariable Long id) {
        surveyService.launchSurvey(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
    }

    @PostMapping("/{surveyId}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDTO addQuestion(
        @PathVariable("surveyId") Long surveyId,
        @Valid @RequestBody CreateQuestionRequest request) {

    return surveyService.addQuestion(surveyId, request);
}


    @GetMapping("/{surveyId}/questions")
    public List<QuestionResponseDTO> getQuestions(@PathVariable("surveyId") Long surveyId) {
    return surveyService.getQuestionsBySurvey(surveyId);
}

    
}