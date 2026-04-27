package com.company.campaign.persistence.repository;

import com.company.campaign.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findBySurveyResponseId(Long surveyResponseId);
}
