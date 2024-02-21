package fr.ubo.dosi.projectagile.cscievaebackend.controller;



import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur pour les opérations CRUD sur les rubriques.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/rubrique")
public class RubriqueController {

    @Autowired
    private RubriqueService rubriqueService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Crée une nouvelle rubrique.
     *
     * @param rubrique La rubrique à créer.
     * @return La rubrique créée.
     */
    @PreAuthorize("hasAuthority('ADM')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Rubrique>> createRubrique(@RequestBody Rubrique rubrique) {
        Rubrique createdRubrique = rubriqueService.creerRubrique(rubrique);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(createdRubrique));
    }

    /**
     * Récupère toutes les rubriques.
     *
     * @return La liste de toutes les rubriques.
     */
    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RubriqueDTO>>> getAllRubrique() {
        List<Rubrique> rubriques = rubriqueService.getAllRubrique();
        return ResponseEntity.ok(ApiResponse.ok(rubriques.stream().map((element) -> modelMapper.map(element, RubriqueDTO.class)).collect(Collectors.toList())));
    }

    /**
     * Récupère les rubriques par type.
     *
     * @param type Le type de rubrique à récupérer.
     * @return La liste des rubriques du type spécifié.
     */
    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<Rubrique>>> getRubriqueByType(@PathVariable("type") String type)  {
        List<Rubrique> rubriques = rubriqueService.getRubriqueByType(type);
        return ResponseEntity.ok(ApiResponse.ok(rubriques));
    }

    /**
     * Récupère une rubrique par son ID.
     *
     * @param id L'ID de la rubrique à récupérer.
     * @return La rubrique avec l'ID spécifié.
     */
    @PreAuthorize("hasAnyAuthority('ADM', 'ENS')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RubriqueDTO>> getRubriqueById(@PathVariable Long id) {
        try {
            Rubrique rubrique = rubriqueService.getRubriqueById(id);
            return ResponseEntity.ok(ApiResponse.ok(modelMapper.map(rubrique, RubriqueDTO.class)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Rubrique not found", null));
        }
    }

    /**
     * Met à jour une rubrique.
     *
     * @param id L'ID de la rubrique à mettre à jour.
     * @param rubrique Les nouvelles informations de la rubrique.
     * @return La rubrique mise à jour.
     */
    @PreAuthorize("hasAuthority('ADM')")
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

    /**
     * Supprime une rubrique.
     *
     * @param id L'ID de la rubrique à supprimer.
     * @return Une réponse vide.
     */
    @PreAuthorize("hasAuthority('ADM')")
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
