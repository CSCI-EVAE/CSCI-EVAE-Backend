package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;

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
public class QualificatifController {

    private final QualificatifService qualificatifService;
    private final ModelMapper modelMapper;

    @Autowired
    public QualificatifController(QualificatifService qualificatifService, ModelMapper modelMapper) {
        this.qualificatifService = qualificatifService;
        this.modelMapper = modelMapper;
    }


    /**
     * Endpoint to get all Qualificatifs.
     * It fetches all the Qualificatifs from the database using the QualificatifService.
     * Each Qualificatif is then mapped to a QualificatifDTO using the ModelMapper.
     * The list of QualificatifDTOs is then wrapped in an ApiResponse and returned.
     *
     * @return ApiResponse containing a list of QualificatifDTOs
     */

    @GetMapping
    public ResponseEntity<?> getQualificatifs() {
        return ApiResponse.ok(qualificatifService.getAllQualificatifs().stream().map((element) -> modelMapper.map(element, QualificatifDTO.class)).collect(Collectors.toList()));
    }


    /**
     * Endpoint to get a Qualificatif by its ID.
     * It fetches the Qualificatif from the database using the QualificatifService.
     * If the Qualificatif is found, it is wrapped in an ApiResponse and returned.
     * If the Qualificatif is not found, an ApiResponse with an error message is returned.
     * If an error occurs while retrieving the Qualificatif, an ApiResponse with an error message is returned.
     *
     * @param id The ID of the Qualificatif to retrieve.
     * @return ApiResponse containing the Qualificatif if found, otherwise an error message.
     */

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

    /**
     * Endpoint pour ajouter un nouveau Qualificatif.
     * Il prend un Qualificatif en tant que corps de la requête HTTP.
     * Le Qualificatif est ensuite passé au service Qualificatif pour être créé.
     * Le Qualificatif créé est ensuite renvoyé enveloppé dans une ApiResponse.
     *
     * @param qualificatif Le Qualificatif à ajouter.
     * @return ApiResponse contenant le Qualificatif ajouté.
     */
    @PostMapping
    public ResponseEntity<?> addQualificatif(@Validated @RequestBody Qualificatif qualificatif, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création du qualificatif", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        }
        return ApiResponse.ok(qualificatifService.createQualificatif(qualificatif));
    }


    /**
     * Endpoint pour mettre à jour un Qualificatif existant.
     * Il prend l'ID du Qualificatif à mettre à jour et le Qualificatif modifié en tant que corps de la requête HTTP.
     * Le Qualificatif modifié est ensuite passé au service Qualificatif pour être mis à jour.
     * Le Qualificatif mis à jour est ensuite renvoyé enveloppé dans une ApiResponse.
     *
     * @param id                  L'ID du Qualificatif à mettre à jour.
     * @param qualificatifModifie Le Qualificatif modifié.
     * @return ApiResponse contenant le Qualificatif mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQualificatif(@Validated @PathVariable Long id, @Validated @RequestBody Qualificatif qualificatifModifie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création du qualificatif", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        }
        Qualificatif updated = qualificatifService.updateQualificatif(id, qualificatifModifie);
        return ApiResponse.ok("Qualificatif mis à jour avec succès", updated);
    }

    /**
     * Endpoint pour supprimer un Qualificatif existant.
     * Il prend l'ID du Qualificatif à supprimer.
     * Si le Qualificatif est trouvé et n'est pas lié à des questions, il est supprimé.
     * Si le Qualificatif est lié à des questions, une erreur est renvoyée.
     * Si le Qualificatif n'est pas trouvé, une erreur est renvoyée.
     * Si une erreur se produit lors de la suppression du Qualificatif, une erreur est renvoyée.
     *
     * @param id L'ID du Qualificatif à supprimer.
     * @return ApiResponse avec un message de succès ou d'erreur.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQualificatif(@Validated @PathVariable Long id) {
        qualificatifService.deleteQualificatif(id);
        return ApiResponse.ok("Qualificatif supprimé avec succès");
    }

}