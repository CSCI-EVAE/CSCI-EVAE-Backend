package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
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

    @PreAuthorize("hasAuthority('ADM')")
    @PostMapping
    public ResponseEntity<ApiResponse<Question>> createQuestion(@RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdQuestion));
    }

    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionDTO>>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        logger.info("questions : " + questions);
        return ResponseEntity.ok(ApiResponse.ok(questions.stream().map((element) -> modelMapper.map(element, QuestionDTO.class)).collect(Collectors.toList())));
    }

    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestionById(id);
            return ResponseEntity.ok(ApiResponse.ok(question));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Question not found", null));
        }
    }

    @PreAuthorize("hasAuthority('ADM')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        try {
            Question updatedQuestion = questionService.updateQuestion(id, question);
            return ResponseEntity.ok(ApiResponse.ok(updatedQuestion));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Question not found", null));
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
                    .body(ApiResponse.error("Question not found", null));
        }
    }
}
