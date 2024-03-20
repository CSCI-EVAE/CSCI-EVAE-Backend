package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.RubriqueMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rubriqueQuestion")
@Tag(name = "RubriqueQuestion", description = "RubriqueQuestion API")
public class RubriqueQuestionController {
    @Autowired
    private RubriqueQuestionService rubriqueQuestionService;

    @Autowired
    private RubriqueMapper rubriqueMapper;

    @Operation(summary = "Get all RubriqueQuestions", description = "Fetches all the RubriqueQuestions from the database and maps them to DTOs")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "RubriqueQuestions fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the RubriqueQuestions", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<?> getQuestionsForAllRubriques() {
        return ApiResponse.ok(rubriqueQuestionService.findAllQuestionsForRubriques().stream().map(rubriqueMapper::rubriqueToRubriqueDTO).collect(Collectors.toList()));
    }

    @Operation(summary = "Get RubriqueQuestions by ID", description = "Fetches the RubriqueQuestion with the given ID from the database and maps it to a DTO")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "RubriqueQuestion fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "RubriqueQuestion not found", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the RubriqueQuestion", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Update RubriqueQuestions", description = "Updates the RubriqueQuestions in the database")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "RubriqueQuestions updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "An error occurred while updating the RubriqueQuestions", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
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

    @Operation(summary = "Delete RubriqueQuestion by ID", description = "Deletes the RubriqueQuestion with the given ID from the database")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "RubriqueQuestion deleted successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "RubriqueQuestion not found", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while deleting the RubriqueQuestion", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
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
