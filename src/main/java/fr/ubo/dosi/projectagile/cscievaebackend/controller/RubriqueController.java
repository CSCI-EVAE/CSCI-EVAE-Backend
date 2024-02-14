package fr.ubo.dosi.projectagile.cscievaebackend.controller;



import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/rubrique")
@PreAuthorize("hasAuthority('ADMIN')")

public class RubriqueController {

    @Autowired
    private RubriqueService rubriqueService;

    @PostMapping
    public ResponseEntity<ApiResponse<Rubrique>> createRubrique(@RequestBody Rubrique rubrique) {
        Rubrique createdRubrique = rubriqueService.creerRubrique(rubrique);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdRubrique));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Rubrique>>> getAllRubrique() {
        List<Rubrique> rubriques = rubriqueService.getAllRubrique();
        return ResponseEntity.ok(ApiResponse.ok(rubriques));
    }

    @GetMapping("/ty/{type}")
    public ResponseEntity<ApiResponse<List<Rubrique>>> getRubriqueByType(@PathVariable("type") String type)  {
        List<Rubrique> rubriques = rubriqueService.getRubriqueByType(type);
        return ResponseEntity.ok(ApiResponse.ok(rubriques));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Rubrique>> getRubriqueById(@PathVariable Long id) {
        try {

            Rubrique rubrique = rubriqueService.getRubriqueById(id);
            return ResponseEntity.ok(ApiResponse.ok(rubrique));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Rubrique not found", null));

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Rubrique>> updateRubrique(@PathVariable Long id, @RequestBody Rubrique rubrique) {
        try {
            Rubrique updatedRubrique = rubriqueService.updateRubrique(id, rubrique);
            return ResponseEntity.ok(ApiResponse.ok(updatedRubrique));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Rubrique not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRubrique(@PathVariable Long id) {
        try {
            rubriqueService.deleteRubrique(id);
            return ResponseEntity.ok(ApiResponse.ok(null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Rubrique not found", null));
        }
    }

}