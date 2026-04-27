package com.company.campaign.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.campaign.model.entity.SurveyResponse;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    boolean existsBySurveyIdAndUserEmail(Long surveyId, String email);
}
