package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.QuestionMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    Logger logger = Logger.getLogger(QuestionController.class.getName());
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @PreAuthorize("hasAuthority('ADM')")
    @PostMapping
    public ResponseEntity<ApiResponse<QuestionDTO>> createQuestion(@RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        if (createdQuestion == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(" Question existe deja", null));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(questionMapper.questionToQuestionDTO(createdQuestion)));
    }

    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        logger.info("questions : " + questions);
        return ResponseEntity.ok(ApiResponse.ok(questions.stream().map(questionMapper::questionToQuestionDTO).collect(Collectors.toList())));
    }

    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionDTO>> getQuestionById(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return ResponseEntity.ok(ApiResponse.ok(questionMapper.questionToQuestionDTO(question)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("la question n'a pas été trouvée", null));
        }
    }

    @PreAuthorize("hasAuthority('ADM')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuestionDTO>> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO question) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id , questionMapper.questionDTOToQuestion(question));
            logger.info("updatedQuestion : " + updatedQuestion);
            if (updatedQuestion == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ApiResponse.error("Question exists deja", null));
            }
            return ResponseEntity.ok(ApiResponse.ok(questionMapper.questionToQuestionDTO(updatedQuestion)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Question pas trouvée", null));
        }
    }



    @PreAuthorize("hasAuthority('ADM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
        try {
           questionService.deleteQuestion(id);
           return ResponseEntity.ok(ApiResponse.ok(null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("la question n'a pas été trouvée ou lien avec une rubrique", null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la suppression de la question", null));
        }
    }
}
