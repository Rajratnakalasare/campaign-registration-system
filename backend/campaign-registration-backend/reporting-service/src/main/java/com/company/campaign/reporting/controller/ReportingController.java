package com.company.campaign.reporting.controller;

import com.company.campaign.reporting.dto.FlattenedReportDTO;
import com.company.campaign.reporting.service.ReportingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/surveys/{surveyId}/responses")
    public FlattenedReportDTO getResponses(
            @PathVariable("surveyId") Long surveyId,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return reportingService.getFlattenedResponses(surveyId, date, startDate, endDate);
    }
}

