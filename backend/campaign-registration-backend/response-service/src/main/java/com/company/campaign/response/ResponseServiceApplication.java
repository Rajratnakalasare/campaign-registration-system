package com.company.campaign.response;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.company.campaign")
@EntityScan(basePackages = "com.company.campaign.model.entity")
@EnableJpaRepositories(basePackages = "com.company.campaign.persistence.repository")
public class ResponseServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ResponseServiceApplication.class, args);
    }
}
