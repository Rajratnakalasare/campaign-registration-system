package com.company.campaign.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "survey_response",
    uniqueConstraints = @UniqueConstraint(columnNames = {"survey_id", "user_email"})
)
public class SurveyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false, length = 150)
    private String userEmail;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answers;

    public SurveyResponse() {}

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public Survey getSurvey() {
        return survey;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}