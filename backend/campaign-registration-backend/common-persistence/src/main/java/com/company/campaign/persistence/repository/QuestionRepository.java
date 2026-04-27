package com.company.campaign.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.campaign.model.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySurveyId(Long surveyId);
}
