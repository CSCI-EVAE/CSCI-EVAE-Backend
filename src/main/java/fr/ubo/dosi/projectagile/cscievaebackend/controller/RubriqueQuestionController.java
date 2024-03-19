package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.RubriqueMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rubriqueQuestion")
public class RubriqueQuestionController {
    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;

    @Autowired
    private RubriqueMapper rubriqueMapper;

    @GetMapping("/all")
    public ResponseEntity<?> getQuestionsForAllRubriques() {
        return ApiResponse.ok(rubriqueQuestionService.findAllQuestionsForRubriques().stream().map(rubriqueMapper::rubriqueToRubriqueDTO).collect(Collectors.toList()));
    }

    @PostMapping("/AjouterRubriqueQuestion")
    public ResponseEntity<?> processRubriqueQuestions(@RequestBody IncomingRubriqueQuestionDTO incomingData) {
        String result = rubriqueQuestionService.AjouterRubriqueQuestion(incomingData);
        if (result.isEmpty()) {
            return ApiResponse.error("Une erreur s'est produite lors de l'ajout de la question à la rubrique");
        }
        if (result.equals("Impossible d'ajouter une rubrique sans questions")) {
            return ApiResponse.error("Impossible d'ajouter une rubrique sans questions");
        }
        return ApiResponse.ok(result);
    }

    @PostMapping("/UpdateRubriqueQuestions")
    public ResponseEntity<?> updateRubriqueQuestions(@RequestBody IncomingRubriqueQuestionDTO incomingData) {
        String result = rubriqueQuestionService.AjouterRubriqueQuestion(incomingData);
        if (result.isEmpty()) {
            return ApiResponse.error("Une erreur s'est produite lors de l'ajout de la question à la rubrique");
        }
        if (result.equals("Impossible d'ajouter une rubrique sans questions")) {
            return ApiResponse.error("Impossible d'ajouter une rubrique sans questions");
        }
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/deleteAll/{id}")
    public ResponseEntity<?> deleteAllRubriqueQuestion(@PathVariable Long id) {
        try {
            String result = rubriqueQuestionService.deleteAllRubriqueQuestion(id);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    }
}
