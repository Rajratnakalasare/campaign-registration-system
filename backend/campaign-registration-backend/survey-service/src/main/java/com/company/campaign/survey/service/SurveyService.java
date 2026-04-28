package com.company.campaign.survey.service;

import com.company.campaign.model.entity.Question;
import com.company.campaign.model.entity.QuestionOption;
import com.company.campaign.model.entity.Survey;
import com.company.campaign.model.enums.SurveyStatus;
import com.company.campaign.persistence.repository.QuestionRepository;
import com.company.campaign.persistence.repository.SurveyRepository;
import com.company.campaign.survey.dto.CreateQuestionRequest;
import com.company.campaign.survey.dto.CreateSurveyRequest;
import com.company.campaign.survey.dto.QuestionResponseDTO;
import com.company.campaign.survey.dto.SurveyResponseDTO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

   private final SurveyRepository surveyRepository;
   private final QuestionRepository questionRepository;

    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    public SurveyResponseDTO createSurvey(CreateSurveyRequest request) {

        Survey survey = new Survey();
        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        survey.setStartDate(request.getStartDate());
        survey.setEndDate(request.getEndDate());

        survey.setStatus(SurveyStatus.CREATED.name());
        survey.setCreatedAt(LocalDateTime.now());

        return mapToDto(surveyRepository.save(survey));
    }

       /* List Surveys */
    public List<SurveyResponseDTO> getAllSurveys() {
           
        return surveyRepository.findAll().stream()
               .map(this::mapToDto)
               .collect(Collectors.toList());
    }

    /* Get Survey by ID */
    public SurveyResponseDTO getSurveyById(Long id) {
        
        Survey survey = surveyRepository.findById(id)
                       .orElseThrow(() -> new RuntimeException("Survey does not found"));

        return mapToDto(survey);
    }

    /* Launch Survey  */
    public void launchSurvey(Long id) {

        Survey survey = surveyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Survey does not found"));  
        
    if("LAUNCHED".equals(survey.getStatus())) {
            throw new RuntimeException("Survey already launched");
        }

        survey.setStatus("LAUNCHED");
        surveyRepository.save(survey);
    }
    
    /*  Delete survey */
    public void deleteSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        if ("LAUNCHED".equals(survey.getStatus())) {
            throw new RuntimeException("Launched survey cannot be deleted");
        }

        surveyRepository.delete(survey);
    }


    private SurveyResponseDTO mapToDto(Survey survey) {

        SurveyResponseDTO dto = new SurveyResponseDTO();
        dto.setId(survey.getId());
        dto.setTitle(survey.getTitle());
        dto.setDescription(survey.getDescription());
        dto.setStarDate(survey.getStartDate());
        dto.setEndDate(survey.getEndDate());
        dto.setStatus(survey.getStatus());
        dto.setCreatedAt(survey.getCreatedAt());

        return dto;
    }

    public QuestionResponseDTO addQuestion(Long surveyId, CreateQuestionRequest request) {

        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

                if("LAUNCHED".equals(survey.getStatus())) {
                    throw new RuntimeException("Cannot add questions to a Launched survey");
                }

        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setMandatory(request.getMandatory());
        question.setSurvey(survey);

        
// Handle options for RADIO / CHECKBOX
    if (request.getOptions() != null && !request.getOptions().isEmpty()) {
        List<QuestionOption> options = request.getOptions()
                .stream()
                .map(opt -> {
                    QuestionOption option = new QuestionOption();
                    option.setOptionValue(opt);
                    option.setQuestion(question);
                    return option;
                })
                .collect(Collectors.toList());
        question.setOptions(options);
    }


        Question saved = questionRepository.save(question);
         return mapQuestionToDTO(saved);

    }

    
public List<QuestionResponseDTO> getQuestionsBySurvey(Long surveyId) {

    return questionRepository.findBySurveyId(surveyId)
            .stream()
            .map(this::mapQuestionToDTO)
            .collect(Collectors.toList());
}

private QuestionResponseDTO mapQuestionToDTO(Question question) {

    QuestionResponseDTO dto = new QuestionResponseDTO();
    dto.setId(question.getId());
    dto.setQuestionText(question.getQuestionText());
    dto.setQuestionType(question.getQuestionType());
    dto.setMandatory(question.getMandatory());

    if (question.getOptions() != null) {
        dto.setOptions(
                question.getOptions()
                        .stream()
                        .map(QuestionOption::getOptionValue)
                        .collect(Collectors.toList())
        );
    }
    return dto;
}
}
