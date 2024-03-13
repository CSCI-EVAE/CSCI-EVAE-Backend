package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.AuthentificationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.EvaluationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {
    private final EvaluationServiceImpl es;
    private final AuthentificationServiceImpl as;
    private final PromotionService ps;
    private final EvaluationMapper evaluationMapper;
    private final ModelMapper modelMapper;
    private final EvaluationService evaluationService;


    @Autowired
    public EvaluationController(EvaluationServiceImpl es, AuthentificationServiceImpl as, PromotionService ps, EvaluationMapper evaluationMapper, ModelMapper modelMapper, EvaluationService evaluationService
    ) {
        this.es = es;
        this.as = as;
        this.ps = ps;
        this.evaluationMapper = evaluationMapper;
        this.modelMapper = modelMapper;
        this.evaluationService = evaluationService;
    }

    /**
     * Endpoint to get all evaluations for the currently authenticated user.
     * The user must have the 'ENS' authority.
     *
     * @param currentUser The currently authenticated user.
     * @return ApiResponse containing a set of EvaluationDTO objects if evaluations exist for the user,
     * otherwise a message indicating no evaluations are available.
     */
    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetails currentUser) {
        Authentification auth = as.getAuhtentification(currentUser.getUsername());
        Set<Evaluation> evaluations = auth.getNoEnseignant().getEvaluations();
        if (evaluations == null) {
            return ApiResponse.ok("Aucune évaluation n'est disponible pour cet enseignant");
        }
        Set<EvaluationDTO> evaluationDTOs = evaluations.stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toSet());
        return ApiResponse.ok(evaluationDTOs);
    }

    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("getAllForThisYear")
    public ResponseEntity<?> getAllForThisYear(@AuthenticationPrincipal UserDetails currentUser, @RequestParam("codeFormation") String codeFormation, @RequestParam("anneeUniversitaire") String anneeUniversitaire) {
        Authentification auth = as.getAuhtentification(currentUser.getUsername());
        Set<Evaluation> evaluations = auth.getNoEnseignant().getEvaluations().stream().filter(evaluation -> evaluation.getPromotion().getId().getCodeFormation().equals(codeFormation) && evaluation.getPromotion().getId().getAnneeUniversitaire().equals(anneeUniversitaire)).collect(Collectors.toSet());
        Set<EvaluationDTO> evaluationDTOs = evaluations.stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toSet());
        return ApiResponse.ok(evaluationDTOs);
    }

    @PreAuthorize("hasAuthority('ENS')")
    @PutMapping("soumettre/{id}")
    public ResponseEntity<?> soumettreEvaluation(@PathVariable Long id) throws ResourceNotFoundException {
        try {

            EvaluationDTO updated = evaluationService.updateEvaluation(id);
            return ApiResponse.ok(updated);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("L'evaluation n'existe pas avec cet id : " + id);
        }
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @GetMapping("details/{Id}")
    public ResponseEntity<?> getDetails(@PathVariable Long Id) {
        try {
            EvaluationDTO evaluationDetails = es.getEvaluationById(Id);
            return ApiResponse.ok(evaluationDetails);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ApiResponse.error("L'evaluation n'existe pas avec cet id : " + Id, null);
        }
    }

    /**
     * Endpoint to get all evaluations for the currently authenticated student.
     * The user must have the 'ETU' authority.
     *
     * @param currentUser The currently authenticated user.
     * @return ApiResponse containing a set of EvaluationDTO objects if evaluations exist for the user,
     * otherwise a message indicating no evaluations are available.
     */
    @PreAuthorize("hasAuthority('ETU')")
    @GetMapping("getEvaluationsByUser")
    public ResponseEntity<?> getEvaluationsByUser(@AuthenticationPrincipal UserDetails currentUser) {
        Etudiant etudiant = as.getAuhtentification(currentUser.getUsername()).getNoEtudiant();
        Set<Evaluation> evaluations = etudiant.getPromotion().getEvaluations().stream().filter(evaluation -> !evaluation.getEtat().equals("ELA")).collect(Collectors.toSet());
        Set<EvaluationDTO> evaluationDTOs = evaluations.stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toSet());
        return ResponseEntity.ok(ApiResponse.ok(evaluationDTOs));
    }

    @PreAuthorize("hasAuthority('ETU')")
    @GetMapping("evaluations/last-year")
    public ResponseEntity<?> getEvaluationsForEnseignantLastYear(@AuthenticationPrincipal UserDetails currentUser) {
        Short enseignantId = as.getAuhtentification(currentUser.getUsername()).getNoEnseignant().getId();
        List<Evaluation> evaluations = es.getEvaluationsForEnseignantLastYear(Long.valueOf(enseignantId));
        List<EvaluationDTO> evaluationDTOs = evaluations.stream().map((element) -> modelMapper.map(element, EvaluationDTO.class)).collect(Collectors.toList());
        return ApiResponse.ok(evaluationDTOs);
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @PostMapping("create")
    public ResponseEntity<?> createEvaluation(@Validated @RequestBody EvaluationSaveDTO evaluationDTO, @AuthenticationPrincipal UserDetails currentUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("/n"));
            return ApiResponse.error("Error creating Evaluation: " + errorMessage);
        }
        Enseignant ens = as.getAuhtentification(currentUser.getUsername()).getNoEnseignant();
        es.saveEvaluation(evaluationDTO, ens);
        return ApiResponse.ok("Evaluation successfully created");
    }


    // Supprimer une evaluation par id et Entrer dans Rubrique_evaluation pour supprimer Question_evaluation et Rubrique_evaluation
    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteEvaluation(@PathVariable Long id) {
        try {
            evaluationService.deleteEvaluation(id);
            return ApiResponse.ok("Evaluation supprimé avec succés ");
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("L'evaluation n'existe pas avec cet id : " + id);
        }
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @GetMapping("getReponses/{idEvaluation}/{noEtudiant}")
    public ResponseEntity<?> getReponses(@PathVariable Long idEvaluation, @PathVariable Long noEtudiant) throws ResourceNotFoundException {
        List<ReponseEvaluation> reponses = evaluationService.getReponsesForEvaluationAndEtudiant(idEvaluation, noEtudiant);
        return ApiResponse.ok(reponses);
    }

}