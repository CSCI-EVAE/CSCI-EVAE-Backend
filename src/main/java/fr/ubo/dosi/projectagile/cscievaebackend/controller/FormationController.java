package fr.ubo.dosi.projectagile.cscievaebackend.controller;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.FormationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('SECRETAIRE')")
@RequestMapping("/api/formations")
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }
    @PostMapping("save")
    public ResponseEntity<ApiResponse<Formation>> saveFormation(@Valid @RequestBody Formation formation) {
        Formation savedFormation = formationService.createFormation(formation);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(savedFormation));
    }
    @GetMapping("/{codeFormation}")
    public ResponseEntity<ApiResponse<?>> getFormation(@PathVariable String codeFormation) {
        try {
            Optional<Formation> formation = formationService.getFormationById(codeFormation);
            if (formation.isPresent()) {
                return ResponseEntity.ok(ApiResponse.ok(formation.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred while fetching the formation.", ex.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllFormations() {
        try {
            List<Formation> formations = formationService.getAllFormations();
            return ResponseEntity.ok(ApiResponse.ok(formations));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred while fetching all formations.", ex.getMessage()));
        }
    }

    @PutMapping("/update/{codeFormation}")
    public ResponseEntity<ApiResponse<?>> updateFormation(
            @PathVariable String codeFormation,
            @RequestBody Formation updatedFormation) {
        try {
            Formation updated = formationService.updateFormation(codeFormation, updatedFormation);
            if (updated != null) {
                return ResponseEntity.ok(ApiResponse.ok(updated));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred while updating the formation.", ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{codeFormation}")
    public ResponseEntity<ApiResponse<?>> deleteFormation(@PathVariable String codeFormation) {
        try {
            boolean deleted = formationService.deleteFormation(codeFormation);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred while deleting the formation.", ex.getMessage()));
        }
    }
}
