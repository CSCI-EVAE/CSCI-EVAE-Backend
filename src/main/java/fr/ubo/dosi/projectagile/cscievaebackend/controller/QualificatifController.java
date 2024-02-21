package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/qualificatif")
public class QualificatifController {

    private final QualificatifService qualificatifService;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @Autowired
    public QualificatifController(QualificatifService qualificatifService, QuestionService questionService, ModelMapper modelMapper) {
        this.qualificatifService = qualificatifService;
        this.questionService = questionService;
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
    public ApiResponse<List<QualificatifDTO>> getQualificatifs() {
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
    public ApiResponse<Qualificatif> getQualificatifById(@Valid @PathVariable Long id) {
        try {
            Optional<Qualificatif> qualificatif = qualificatifService.getQualificatifById(id);
            return qualificatif.map(ApiResponse::ok)
                    .orElse(ApiResponse.error("Aucun qualificatif trouvé avec cet ID",
                            null));
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("Une erreur s'est produite lors de la récupération du qualificatif",
                    null);
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
    public ApiResponse<Qualificatif> addQualificatif(@RequestBody Qualificatif qualificatif) {
        Qualificatif qualificatifAjouter = qualificatifService.createQualificatif(qualificatif);
        return ApiResponse.ok(qualificatifAjouter);
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
    public ApiResponse<Qualificatif> updateQualificatif(@PathVariable Long id, @RequestBody Qualificatif qualificatifModifie) {
        try {
            Qualificatif updated = qualificatifService.updateQualificatif(id, qualificatifModifie);
            return ApiResponse.ok(updated);

        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("Qualificatif not found", null);
        }
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
    public ApiResponse<Void> deleteQualificatif(@PathVariable Long id) {
        try {
            Optional<Qualificatif> qualificatif = qualificatifService.getQualificatifById(id);
            if (qualificatif.isPresent()) {
                List<Question> relatedQuestions = questionService.findQuestionsByQualificatifId(qualificatif.get());
                if (!relatedQuestions.isEmpty()) {
                    return ApiResponse.error("Ce qualificatif ne peut pas etre supprimer car il est lié a " + relatedQuestions.size() + " Questions", null);
                } else {
                    qualificatifService.deleteQualificatif(id);
                    return ApiResponse.ok(null);
                }
            } else {
                return ApiResponse.error("Ce qualificatif n'existe pas", null);

            }
        } catch (SQLException e) {
            return ApiResponse.error("Error deleting qualificatif", null);
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("Qualificatif not found", null);
        }
    }
}