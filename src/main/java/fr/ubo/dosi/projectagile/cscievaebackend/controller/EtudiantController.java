package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;

@RestController
@PreAuthorize("hasAuthority('SECRETAIRE')")
@RequestMapping("/api/v1/etudiants")
public class EtudiantController {
    @Autowired
    private EtudiantService etudiantService;
    Logger logger  = Logger.getLogger(EtudiantController.class.getName()) ;

    @PostMapping
    public ResponseEntity<ApiResponse<Etudiant>> createEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant createdEtudiant = etudiantService.createEtudiant(etudiant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdEtudiant));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Etudiant>>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        logger.info("etudiants : " + etudiants);
        return ResponseEntity.ok(ApiResponse.ok(etudiants));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Etudiant>> getEtudiantById(@PathVariable String id) {
        try {
            Etudiant etudiant = etudiantService.getEtudiantById(id);
            return ResponseEntity.ok(ApiResponse.ok(etudiant));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Etudiant not found", null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Etudiant>> updateEtudiant(@PathVariable String id, @RequestBody Etudiant etudiant) {
        Etudiant updatedEtudiant = etudiantService.updateEtudiant(id, etudiant);
        return ResponseEntity.ok(ApiResponse.ok(updatedEtudiant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEtudiant(@PathVariable String id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
