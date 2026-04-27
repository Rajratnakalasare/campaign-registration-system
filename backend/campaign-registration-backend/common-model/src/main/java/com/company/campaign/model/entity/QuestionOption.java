package com.company.campaign.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_option")
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String optionValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public QuestionOption() {}

    public Long getId() {
        return id;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
