package com.company.campaign.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.campaign.model.entity.SurveyResponse;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    boolean existsBySurveyIdAndUserEmail(Long surveyId, String email);
    List<SurveyResponse> findBySurveyId(Long surveyId);
    
    List<SurveyResponse> findBySurveyIdAndSubmittedAtBetween(
        Long surveyId, LocalDateTime start, LocalDateTime end
);

}
