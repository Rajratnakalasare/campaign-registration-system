package com.company.campaign.survey;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.company.campaign.model.entity")
@EnableJpaRepositories(basePackages = "com.company.campaign.persistence.repository")
public class SurveyServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SurveyServiceApplication.class, args);
    }
}
