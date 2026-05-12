package com.company.campaign.reporting.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.campaign.model.entity.Answer;
import com.company.campaign.model.entity.Question;
import com.company.campaign.model.entity.Survey;
import com.company.campaign.model.entity.SurveyResponse;
import com.company.campaign.persistence.repository.AnswerRepository;
import com.company.campaign.persistence.repository.QuestionRepository;
import com.company.campaign.persistence.repository.SurveyRepository;
import com.company.campaign.persistence.repository.SurveyResponseRepository;
import com.company.campaign.reporting.dto.FlattenedReportDTO;

@Service
public class ReportingService {
    
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final AnswerRepository answerRepository;

    public ReportingService(SurveyRepository surveyRepository, QuestionRepository questionRepository,
            SurveyResponseRepository surveyResponseRepository, AnswerRepository answerRepository) {
                this.surveyRepository = surveyRepository;
                this.questionRepository = questionRepository;
                this.surveyResponseRepository = surveyResponseRepository;
                this.answerRepository = answerRepository;
            }
    

    public FlattenedReportDTO getFlattenedResponses(Long surveyId, LocalDate date, LocalDate startDate, LocalDate endDate) {
        
        // 1) Validate survey exists
        Survey survey = surveyRepository.findById(surveyId)
            .orElseThrow(() -> new RuntimeException("Survey not found"));

        // 2) Fetch questions (column)
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        // 3) Compute date-time range if filters present
        LocalDateTime from = null;
        LocalDateTime to = null;

        if(date != null) {
            from = date.atStartOfDay();
            to = date.plusDays(1).atStartOfDay().minusNanos(1);
        } else if(startDate != null && endDate != null){
            from = startDate.atStartOfDay();
            to = endDate.plusDays(1).atStartOfDay().minusNanos(1);

        }

        // 4) Fetch Responses
        List<SurveyResponse> responses;
        if(from != null && to != null) {
            responses = surveyResponseRepository.findBySurveyIdAndSubmittedAtBetween(surveyId, from, to);
        } else {
            responses = surveyResponseRepository.findBySurveyId(surveyId);
        }

        // if no responses, return empty with columns
        
        List<String> columns = buildColumns (questions);

        if (responses.isEmpty()) {
            FlattenedReportDTO dto = new FlattenedReportDTO();
            dto.setColumns(columns);
            dto.setRows(Collections.emptyList());
            return dto;
        }

        // 5) Fetch answers in bulk
        List<Long> responseIds = responses.stream().map(SurveyResponse::getId).collect(Collectors.toList());
        List<Answer> answers = answerRepository.findBySurveyResponseIdIn(responseIds);

        // 6) Build map: responseId -> questionId -> answerValue
        Map<Long, Map<Long, String>> answersMap = new HashMap<>();
        for (Answer a : answers) {
            Long rId = a.getSurveyResponse().getId();
            Long qId = a.getQuestion().getId();

            answersMap.computeIfAbsent(rId, k -> new HashMap<>());

            // If checkbox stored as multiple answer rows, merge them
            answersMap.get(rId).merge(qId, a.getAnswerValue(), (oldVal, newVal) -> oldVal + ", " + newVal);
        }
        
        
        // 7) Build flattened rows
        List<Map<String, String>> rows = new ArrayList<>();

        for (SurveyResponse r : responses) {
            Map<String, String> row = new LinkedHashMap<>();
            row.put("firstName", r.getFirstName());
            row.put("email", r.getUserEmail());
            row.put("submittedAt", String.valueOf(r.getSubmittedAt()));

            Map<Long, String> qAnswerMap = answersMap.getOrDefault(r.getId(), Collections.emptyMap());

            for (Question q : questions) {
                String colName = buildQuestionColumnName(q);
                row.put(colName, qAnswerMap.getOrDefault(q.getId(), ""));
            }

            rows.add(row);
        }
        
        FlattenedReportDTO dto = new FlattenedReportDTO();
        dto.setColumns(columns);
        dto.setRows(rows);
        return dto;
    }

    private List<String> buildColumns(List<Question> questions) {
        List<String> cols = new ArrayList<>();
        cols.add("firstName");
        cols.add("email");
        cols.add("submittedAt");
        for (Question q : questions) {
            cols.add(buildQuestionColumnName(q));
        }
        return cols;
    }

    
private String buildQuestionColumnName(Question q) {
        // Keep headers readable + stable
        return "Q" + q.getId() + " - " + q.getQuestionText();
    }



    }
