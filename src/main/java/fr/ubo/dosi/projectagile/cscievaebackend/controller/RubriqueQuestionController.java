package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionsDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.RubriqueMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rubriqueQuestion")
public class RubriqueQuestionController {
    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;

    Logger logger = Logger.getLogger(RubriqueQuestionController.class.getName());
    @Autowired
    private RubriqueMapper rubriqueMapper;

    @GetMapping("/all")
    public ResponseEntity<?> getQuestionsForAllRubriques() {
        return ApiResponse.ok(rubriqueQuestionService.findAllQuestionsForRubriques().stream().map(rubriqueMapper::rubriqueToRubriqueDTO).collect(Collectors.toList()));
    }

    @PostMapping("/process")
    public ResponseEntity<?> processRubriqueQuestions(@RequestBody List<IncomingRubriqueQuestionDTO> incomingData) {
        String result = rubriqueQuestionService.processAndStore(incomingData);

        if (result.equals("tout les données sont bien enregistrées")) {
            return ApiResponse.ok(result);
        } else {
            return ApiResponse.error(result, null);
        }
    }

    @PostMapping("/AjouterRubriqueQuestion")
    public ResponseEntity<?> processRubriqueQuestions(@RequestBody IncomingRubriqueQuestionDTO incomingData) {
        String result = rubriqueQuestionService.AjouterRubriqueQuestion(incomingData);

        if (result.equals("tout les données sont bien enregistrées")) {
            return ApiResponse.ok(result);
        } else {
            return ApiResponse.error(result, null);
        }
    }

    @PostMapping("/UpdateRubriqueQuestions")
    public ResponseEntity<?> updateRubriqueQuestions(@RequestBody IncomingRubriqueQuestionDTO incomingData) {
        try {
            String result = rubriqueQuestionService.updateRubriqueQuestions(incomingData);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
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
