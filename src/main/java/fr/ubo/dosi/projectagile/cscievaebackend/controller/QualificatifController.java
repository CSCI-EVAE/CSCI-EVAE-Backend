package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/qualificatif")
@Tag(name = "Qualificatif", description = "Qualificatif API")
public class QualificatifController {

    private final QualificatifService qualificatifService;
    private final ModelMapper modelMapper;

    @Autowired
    public QualificatifController(QualificatifService qualificatifService, ModelMapper modelMapper) {
        this.qualificatifService = qualificatifService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all Qualificatifs", description = "Fetches all the Qualificatifs from the database and maps them to DTOs")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Qualificatifs fetched successfully", content = @Content(schema = @Schema(implementation = QualificatifDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Qualificatifs", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<?> getQualificatifs() {
        return fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse.ok(qualificatifService.getAllQualificatifs().stream().map((element) -> modelMapper.map(element, QualificatifDTO.class)).collect(Collectors.toList()));
    }


    @Operation(summary = "Get Qualificatif by ID", description = "Fetches the Qualificatif with the given ID from the database and maps it to a DTO")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Qualificatif fetched successfully", content = @Content(schema = @Schema(implementation = QualificatifDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Qualificatif not found", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Qualificatif", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getQualificatifById(@Valid @PathVariable Long id) {
        try {
            Optional<Qualificatif> qualificatif = qualificatifService.getQualificatifById(id);
            if (qualificatif.isPresent()) {
                return ApiResponse.ok(modelMapper.map(qualificatif.get(), QualificatifDTO.class));
            } else {
                return ApiResponse.error("Qualificatif non trouvé");
            }
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("Une erreur s'est produite lors de la récupération du qualificatif");
        }
    }


    @Operation(summary = "Add a new Qualificatif", description = "Creates a new Qualificatif in the database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Qualificatif created successfully", content = @Content(schema = @Schema(implementation = QualificatifDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "An error occurred while creating the Qualificatif", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while creating the Qualificatif", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<?> addQualificatif(@Validated @RequestBody Qualificatif qualificatif, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création du qualificatif : " + bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return ApiResponse.ok(qualificatifService.createQualificatif(qualificatif));
    }

    @Operation(summary = "Update a Qualificatif", description = "Updates a Qualificatif in the database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Qualificatif updated successfully", content = @Content(schema = @Schema(implementation = QualificatifDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "An error occurred while updating the Qualificatif", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while updating the Qualificatif", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQualificatif(@Validated @PathVariable Long id, @Validated @RequestBody Qualificatif qualificatifModifie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création du qualificatif : " + bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        Qualificatif updated = qualificatifService.updateQualificatif(id, qualificatifModifie);
        return ApiResponse.ok("Qualificatif mis à jour avec succès", updated);
    }

    @Operation(summary = "Delete a Qualificatif", description = "Deletes a Qualificatif from the database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Qualificatif deleted successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while deleting the Qualificatif", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQualificatif(@Validated @PathVariable Long id) {
        qualificatifService.deleteQualificatif(id);
        return ApiResponse.ok("Qualificatif supprimé avec succès");
    }

}