package fr.ubo.dosi.projectagile.cscievaebackend.controller;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
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

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/rubriqueQuestion")
public class RubriqueQuestionController {
    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;

    Logger logger = Logger.getLogger(RubriqueQuestionController.class.getName());

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RubriqueQuestionDTO>>> getAllRubriqueQuestions() {
        List<RubriqueQuestionDTO> rubriqueQuestions = rubriqueQuestionService.getAllRubriqueQuestions();
        logger.info("rubriqueQuestions : " + rubriqueQuestions);
        return ResponseEntity.ok(ApiResponse.ok(rubriqueQuestions));
    }

    @GetMapping("/{idQuestion}/{idRubrique}")
    public ResponseEntity<ApiResponse<RubriqueQuestionDTO>> getRubriqueQuestionById(@PathVariable Long idQuestion, @PathVariable Long idRubrique) {
        RubriqueQuestionId rubriqueQuestionId = new RubriqueQuestionId(idQuestion, idRubrique);
        RubriqueQuestionDTO rubriqueQuestionDTO = rubriqueQuestionService.getRubriqueQuestionById(rubriqueQuestionId);
        if (rubriqueQuestionDTO != null) {
            return ResponseEntity.ok(ApiResponse.ok(rubriqueQuestionDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Rubrique Question not found", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<RubriqueQuestionDTO>> addRubriqueQuestion(@RequestBody RubriqueQuestionDTO rubriqueQuestionAddDTO) {
        RubriqueQuestionDTO createdRubriqueQuestion = rubriqueQuestionService.addRubriqueQuestion(rubriqueQuestionAddDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdRubriqueQuestion));
    }

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
}
