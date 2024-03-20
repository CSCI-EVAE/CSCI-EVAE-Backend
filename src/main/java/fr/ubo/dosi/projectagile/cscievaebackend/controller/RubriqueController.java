package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rubrique")
@Tag(name = "Rubrique", description = "Rubrique API")
public class RubriqueController {

    @Autowired
    private RubriqueService rubriqueService;
    @Autowired
    private ModelMapper modelMapper;


    @Operation(summary = "Create a new Rubrique", description = "Creates a new Rubrique and saves it to the database")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rubrique created successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Rubrique.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "An error occurred while creating the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<?> createRubrique(@Validated @RequestBody Rubrique rubrique, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création de la rubrique : " +
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return ApiResponse.ok(rubriqueService.creerRubrique(rubrique));
    }

    @Operation(summary = "Get all Rubriques", description = "Fetches all the Rubriques from the database and maps them to DTOs")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rubriques fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RubriqueDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Rubriques", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllRubrique() {
        return ApiResponse.ok(rubriqueService.getAllRubrique().stream().map((element) -> modelMapper.map(element, RubriqueDTO.class)).collect(Collectors.toList()));
    }

    @Operation(summary = "Get Rubrique by type", description = "Fetches the Rubrique with the given type from the database and maps it to a DTO")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rubrique fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RubriqueDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Rubrique not found", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getRubriqueByType(@PathVariable("type") String type) {
        List<Rubrique> rubriques = rubriqueService.getRubriqueByType(type);
        return ResponseEntity.ok(ApiResponse.ok(rubriques.stream().map((element) -> modelMapper.map(element, RubriqueDTO.class)).collect(Collectors.toList())));
    }

    @Operation(summary = "Get Rubrique by ID", description = "Fetches the Rubrique with the given ID from the database and maps it to a DTO")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rubrique fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RubriqueDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Rubrique not found", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRubriqueById(@PathVariable Long id) {
        try {
            Rubrique rubrique = rubriqueService.getRubriqueById(id);
            return ApiResponse.ok(modelMapper.map(rubrique, RubriqueDTO.class));
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error("La rubrique n'a pas été trouvée");
        }
    }

    @Operation(summary = "Update a Rubrique", description = "Updates a Rubrique in the database")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rubrique updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RubriqueDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "An error occurred while updating the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while updating the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRubrique(@PathVariable Long id, @Validated @RequestBody Rubrique rubrique, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la modification de la rubrique : " +
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        try {
            Rubrique updatedRubrique = rubriqueService.updateRubrique(id, rubrique);
            return ApiResponse.ok(modelMapper.map(updatedRubrique, RubriqueDTO.class));
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error("La rubrique n'a pas été trouvée ou lien avec une rubrique");
        } catch (IllegalArgumentException ex) {
            return ApiResponse.error(ex.getMessage());
        } catch (Exception ex) {
            return ApiResponse.error("Une erreur s'est produite lors de la mise à jour de la rubrique");
        }
    }

    @Operation(summary = "Delete a Rubrique", description = "Deletes a Rubrique from the database")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Rubrique deleted successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "An error occurred while deleting the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while deleting the Rubrique", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRubrique(@PathVariable Long id) {
        try {
            rubriqueService.deleteRubrique(id);
            return ApiResponse.ok("La rubrique a été supprimée avec succès");
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error("La rubrique n'a pas été trouvée ou lien avec une rubrique");
        } catch (Exception ex) {
            return ApiResponse.error("La rubrique n'a pas été supprimée car elle est liée à une autre ressource");
        }
    }
}
