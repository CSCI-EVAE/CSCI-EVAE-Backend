package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.logging.Logger;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    Logger logger = Logger.getLogger(QuestionController.class.getName());

    @PostMapping
    public ResponseEntity<ApiResponse<Question>> createQuestion(@RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdQuestion));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Question>>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        logger.info("questions : " + questions);
        return ResponseEntity.ok(ApiResponse.ok(questions));
    }

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
