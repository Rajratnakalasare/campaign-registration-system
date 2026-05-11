package com.company.campaign.response.service;

import com.company.campaign.model.entity.*;
import com.company.campaign.persistence.repository.*;

import com.company.campaign.response.dto.*;
import com.company.campaign.response.exception.BadRequestException;
import com.company.campaign.response.exception.ConflictException;
import com.company.campaign.response.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublicSurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final AnswerRepository answerRepository;

    public PublicSurveyService(SurveyRepository surveyRepository,
                               QuestionRepository questionRepository,
                               SurveyResponseRepository surveyResponseRepository,
                               AnswerRepository answerRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.answerRepository = answerRepository;
    }

    public SurveyFormResponseDTO getSurveyForm(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));

        // Survey must be launched and within validity
        validateSurveyIsOpen(survey);

        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        SurveyFormResponseDTO dto = new SurveyFormResponseDTO();
        dto.setSurveyId(survey.getId());
        dto.setTitle(survey.getTitle());
        dto.setDescription(survey.getDescription());
        dto.setStartDate(survey.getStartDate());
        dto.setEndDate(survey.getEndDate());
        dto.setStatus(survey.getStatus());
        dto.setQuestions(questions.stream().map(this::mapQuestion).collect(Collectors.toList()));
        return dto;
    }

    @Transactional
    public SubmitSurveyResponseDTO submitSurvey(Long surveyId, SubmitSurveyRequestDTO request) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));

        validateSurveyIsOpen(survey);

        // One submission per email per survey
        if (surveyResponseRepository.existsBySurveyIdAndUserEmail(surveyId, request.getEmail())) {
            throw new ConflictException("Survey already submitted with this email");
        }

        // Validate mandatory questions answered
        validateAnswers(surveyId, request.getAnswers());

        // Save SurveyResponse
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setSurvey(survey);
        surveyResponse.setFirstName(request.getFirstName());
        surveyResponse.setUserEmail(request.getEmail());
        surveyResponse.setSubmittedAt(LocalDateTime.now());

        // If your SurveyResponse entity has firstName, set it (recommended)
        surveyResponse.setFirstName(request.getFirstName());

        SurveyResponse savedResponse = surveyResponseRepository.save(surveyResponse);

        // Save Answers
        for (AnswerDTO a : request.getAnswers()) {
            Question q = new Question();
            q.setId(a.getQuestionId()); // lightweight reference (no DB hit)

            Answer ans = new Answer();
            ans.setSurveyResponse(savedResponse);
            ans.setQuestion(q);
            ans.setAnswerValue(a.getAnswerValue());
            answerRepository.save(ans);
        }

        SubmitSurveyResponseDTO resp = new SubmitSurveyResponseDTO();
        resp.setMessage("Survey submitted successfully");
        resp.setResponseId(savedResponse.getId());
        resp.setSubmittedAt(savedResponse.getSubmittedAt());
        return resp;
    }

    private void validateSurveyIsOpen(Survey survey) {
        if (!"LAUNCHED".equals(survey.getStatus())) {
            throw new BadRequestException("Survey is not launched");
        }
        LocalDate today = LocalDate.now();
        if (today.isBefore(survey.getStartDate()) || today.isAfter(survey.getEndDate())) {
            throw new BadRequestException("Survey is not active");
        }
    }

    private void validateAnswers(Long surveyId, List<AnswerDTO> answers) {
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        // Ensure answers provided for all mandatory questions
        Set<Long> answeredQIds = answers.stream()
                .map(AnswerDTO::getQuestionId)
                .collect(Collectors.toSet());

        for (Question q : questions) {
            if (Boolean.TRUE.equals(q.getMandatory()) && !answeredQIds.contains(q.getId())) {
                throw new BadRequestException("Missing answer for mandatory question: " + q.getQuestionText());
            }
        }

        // Ensure all questionIds exist in this survey
        for (AnswerDTO a : answers) {
            if (!questionMap.containsKey(a.getQuestionId())) {
                throw new BadRequestException("Invalid questionId: " + a.getQuestionId());
            }
        }
    }

    private QuestionDTO mapQuestion(Question q) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(q.getId());
        dto.setQuestionText(q.getQuestionText());
        dto.setQuestionType(q.getQuestionType());
        dto.setMandatory(q.getMandatory());

        if (q.getOptions() != null) {
            dto.setOptions(q.getOptions().stream()
                    .map(QuestionOption::getOptionValue)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
