package com.company.campaign.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.campaign.model.entity.QuestionOption;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long >{

    List<QuestionOption> findByQuestionId(Long questionId);
}
