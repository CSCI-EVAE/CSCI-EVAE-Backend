package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionsDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/rubriqueQuestion")
public class RubriqueQuestionController {
    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;

    Logger logger = Logger.getLogger(RubriqueQuestionController.class.getName());

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @GetMapping("/all")
    public ApiResponse<List<RubriqueQuestionsDTO>> getQuestionsForAllRubriques() {
        return ApiResponse.ok(rubriqueQuestionService.findAllQuestionsForRubriques());
    }


    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @GetMapping("/{idQuestion}/{idRubrique}")
    public ResponseEntity<ApiResponse<RubriqueQuestionDTO>> getRubriqueQuestionById(@PathVariable Long idQuestion, @PathVariable Long idRubrique) {
        RubriqueQuestionId rubriqueQuestionId = new RubriqueQuestionId(idQuestion.intValue(), idRubrique.intValue());
        RubriqueQuestionDTO rubriqueQuestionDTO = rubriqueQuestionService.getRubriqueQuestionById(rubriqueQuestionId);
        if (rubriqueQuestionDTO != null) {
            return ResponseEntity.ok(ApiResponse.ok(rubriqueQuestionDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Rubrique Question not found", null));
        }
    }

    @PreAuthorize("hasAuthority('ADM')")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteRubriqueQuestion(@RequestBody RubriqueQuestion rubriqueQuestion) {
        try {
            rubriqueQuestionService.deleteRubriqueQuestion(rubriqueQuestion);
            return ResponseEntity.ok(ApiResponse.ok(null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting Rubrique Question", null));
        }
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @PostMapping("/process")
    public ApiResponse<String> processRubriqueQuestions(@RequestBody List<IncomingRubriqueQuestionDTO> incomingData) {
        String result = rubriqueQuestionService.processAndStore(incomingData);

        if (result.equals("tout les données sont bien enregistrées")) {
            return ApiResponse.ok(result);
        } else {
            return ApiResponse.error(result, null);
        }
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @PostMapping("/AjouterRubriqueQuestion")
    public ApiResponse<String> processRubriqueQuestions(@RequestBody IncomingRubriqueQuestionDTO incomingData) {
        String result = rubriqueQuestionService.AjouterRubriqueQuestion(incomingData);

        if (result.equals("tout les données sont bien enregistrées")) {
            return ApiResponse.ok(result);
        } else {
            return ApiResponse.error(result, null);
        }
    }

    // ajouter update rubrique question
    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @PostMapping("/update")
    public ApiResponse<RubriqueQuestion> addRubriqueQuestion(@RequestBody RubriqueQuestion rubriqueQuestion) {
        RubriqueQuestion rubriqueQuestionAjouter = rubriqueQuestionService.saveRubriqueQuestion(rubriqueQuestion);
        return ApiResponse.ok(rubriqueQuestionAjouter);
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @PostMapping("/UpdateRubriqueQuestions")
    public ApiResponse<String> updateRubriqueQuestions(@RequestBody IncomingRubriqueQuestionDTO incomingData) {
        try {
            String result = rubriqueQuestionService.updateRubriqueQuestions(incomingData);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    }
    //delete all the rubrique question where rubrique id = id
    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @DeleteMapping("/deleteAll/{id}")
    public ApiResponse<String> deleteAllRubriqueQuestion(@PathVariable Long id) {
        try {
            String result = rubriqueQuestionService.deleteAllRubriqueQuestion(id);
            return ApiResponse.ok(result);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    }
}
