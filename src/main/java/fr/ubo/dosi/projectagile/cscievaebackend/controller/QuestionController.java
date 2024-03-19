package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création de la Question : "+ bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return ApiResponse.ok(questionService.createQuestion(question));
    }

    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ApiResponse.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@Validated @PathVariable Long id) {
        return ApiResponse.ok(questionService.getQuestionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody @Validated Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la modification de la Question :"+ bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return ApiResponse.ok(questionService.updateQuestion(id, question));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ApiResponse.ok("La question a été supprimée avec succès .");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("La question n'a pas été trouvée ou lien avec une rubrique"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("La question n'a pas été supprimée car elle est liée à une rubrique"));
        }
    }
}
