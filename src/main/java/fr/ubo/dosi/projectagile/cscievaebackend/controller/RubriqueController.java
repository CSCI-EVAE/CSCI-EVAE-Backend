package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur pour les opérations CRUD sur les rubriques.
 */
@RestController
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
    @PostMapping("/create")
    public ResponseEntity<?> createRubrique(@Validated @RequestBody Rubrique rubrique, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création du rubrique : "+
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return ApiResponse.ok(rubriqueService.creerRubrique(rubrique));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRubrique() {
        return ApiResponse.ok(rubriqueService.getAllRubrique().stream().map((element) -> modelMapper.map(element, RubriqueDTO.class)).collect(Collectors.toList()));
    }

    /**
     * Récupère les rubriques par type.
     *
     * @param type Le type de rubrique à récupérer.
     * @return La liste des rubriques du type spécifié.
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getRubriqueByType(@PathVariable("type") String type) {
        List<Rubrique> rubriques = rubriqueService.getRubriqueByType(type);
        return ResponseEntity.ok(ApiResponse.ok(rubriques.stream().map((element) -> modelMapper.map(element, RubriqueDTO.class)).collect(Collectors.toList())));
    }

    /**
     * Récupère une rubrique par son ID.
     *
     * @param id L'ID de la rubrique à récupérer.
     * @return La rubrique avec l'ID spécifié.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRubriqueById(@PathVariable Long id) {
        try {
            Rubrique rubrique = rubriqueService.getRubriqueById(id);
            return ApiResponse.ok(modelMapper.map(rubrique, RubriqueDTO.class));
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error("La rubrique n'a pas été trouvée");
        }
    }

    /**
     * Met à jour une rubrique.
     *
     * @param id       L'ID de la rubrique à mettre à jour.
     * @param rubrique Les nouvelles informations de la rubrique.
     * @return La rubrique mise à jour.
     */
    @PutMapping(value ="/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRubrique(@PathVariable Long id, @Validated @RequestBody Rubrique rubrique, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la modification du rubrique : "+
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        try {
            Rubrique updatedRubrique = rubriqueService.updateRubrique(id, rubrique);
            return ApiResponse.ok(modelMapper.map(updatedRubrique, RubriqueDTO.class));
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error("La rubrique n'a pas été trouvée ou lien avec une rubrique");
        } catch (IllegalArgumentException ex) {
            return ApiResponse.error(ex.getMessage());
        }
        catch (Exception ex) {
            return ApiResponse.error("Une erreur s'est produite lors de la mise à jour de la rubrique");
        }
    }

    /**
     * Supprime une rubrique.
     *
     * @param id L'ID de la rubrique à supprimer.
     * @return Une réponse vide.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRubrique(@PathVariable Long id) {
        try {
            rubriqueService.deleteRubrique(id);
            return ApiResponse.ok("La rubrique a été supprimée avec succès");
        } catch (ResourceNotFoundException ex) {
            return ApiResponse.error("La rubrique n'a pas été trouvée ou lien avec une rubrique");
        } catch (Exception ex) {
            return ApiResponse.error("Une erreur s'est produite lors de la suppression de la rubrique");
        }
    }
}
