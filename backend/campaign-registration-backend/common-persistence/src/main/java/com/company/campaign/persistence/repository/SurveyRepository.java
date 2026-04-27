package com.company.campaign.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.campaign.model.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long>{

}
