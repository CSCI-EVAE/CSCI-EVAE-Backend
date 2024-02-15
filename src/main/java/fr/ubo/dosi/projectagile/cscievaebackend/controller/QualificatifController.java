package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/api/v1/admin/qualificatif")
public class QualificatifController {

    @Autowired
    private final QualificatifService qualificatifService;

    public QualificatifController(QualificatifService qualificatifService) {
        this.qualificatifService = qualificatifService;
    }

    @GetMapping
    public ApiResponse<List<Qualificatif>> getQualificatifs() {
        List<Qualificatif> qualificatifs = qualificatifService.getAllQualificatifs();
        return ApiResponse.ok(qualificatifs);
    }

    @GetMapping("/{id}")
    public ApiResponse<Qualificatif> getQualificatifById(@PathVariable Long id) {
        try {
            Optional<Qualificatif> qualificatif = qualificatifService.getQualificatifById(id);
            return qualificatif.map(ApiResponse::ok)
                    .orElse(ApiResponse.error("Qualificatif not found", null));
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("Error retrieving Qualificatif", null);
        }
    }

    @PostMapping
    public ApiResponse<Qualificatif> addQualificatif(@RequestBody Qualificatif qualificatif) {
        Qualificatif qualificatifAjouter = qualificatifService.createQualificatif(qualificatif);
        return ApiResponse.ok(qualificatifAjouter);
    }

    @PutMapping("/{id}")
    public ApiResponse<Qualificatif> updateQualificatif(@PathVariable Long id, @RequestBody Qualificatif qualificatifModifie) throws ResourceNotFoundException {
        try {
            Qualificatif updated = qualificatifService.updateQualificatif(id, qualificatifModifie);
            return ApiResponse.ok(updated);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Qualificatif not found");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteQualificatif(@PathVariable Long id) {
        try {
            qualificatifService.deleteQualificatif(id);
            return ApiResponse.ok(null);
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("Qualificatif not found", null);
        }
    }
}

