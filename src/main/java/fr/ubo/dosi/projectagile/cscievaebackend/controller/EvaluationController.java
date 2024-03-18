package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.AuthentificationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
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

    private final AuthentificationService authentificationService;
    private final EvaluationMapper evaluationMapper;
    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(AuthentificationService authentificationService, EvaluationMapper evaluationMapper, EvaluationService evaluationService) {
        this.authentificationService = authentificationService;
        this.evaluationMapper = evaluationMapper;
        this.evaluationService = evaluationService;
    }

    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal UserDetails currentUser) {
        Authentification auth = authentificationService.getAuhtentification(currentUser.getUsername());
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
        Authentification auth = authentificationService.getAuhtentification(currentUser.getUsername());
        Set<Evaluation> evaluations = auth.getNoEnseignant().getEvaluations().stream().filter(evaluation -> evaluation.getPromotion().getId().getCodeFormation().equals(codeFormation) && evaluation.getPromotion().getId().getAnneeUniversitaire().equals(anneeUniversitaire)).collect(Collectors.toSet());
        Set<EvaluationDTO> evaluationDTOs = evaluations.stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toSet());
        return ApiResponse.ok(evaluationDTOs);
    }

    @PreAuthorize("hasAuthority('ENS')")
    @PutMapping("soumettre/{id}")
    public ResponseEntity<?> soumettreEvaluation(@PathVariable Long id) {
        try {
            EvaluationDTO updated = evaluationService.updateEvaluation(id);
            return ApiResponse.ok(updated);
        } catch (ResourceNotFoundException e) {
            return ApiResponse.error("L'evaluation n'existe pas avec cet id : " + id);
        }
    }

    @GetMapping("details/{Id}")
    public ResponseEntity<?> getDetails(@PathVariable Long Id) {
        try {
            EvaluationDTO evaluationDetails = evaluationService.getEvaluationById(Id);
            return ApiResponse.ok(evaluationDetails);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ApiResponse.error("L'evaluation n'existe pas avec cet id : " + Id, null);
        }
    }

    @PreAuthorize("hasAuthority('ETU')")
    @GetMapping("getEvaluationsByUser")
    public ResponseEntity<?> getEvaluationsByUser(@AuthenticationPrincipal UserDetails currentUser) {
        Etudiant etudiant = authentificationService.getAuhtentification(currentUser.getUsername()).getNoEtudiant();
        Set<Evaluation> evaluations = etudiant.getPromotion().getEvaluations().stream().filter(evaluation -> !evaluation.getEtat().equals("ELA")).collect(Collectors.toSet());
        Set<EvaluationDTO> evaluationDTOs = evaluations.stream().map(eva -> {
            EvaluationDTO evaluationDTO = evaluationMapper.evaluationToEvaluationDTO(eva);
            if (eva.getReponseEvaluations().stream().anyMatch(reponseEvaluation -> reponseEvaluation.getNoEtudiant().equals(etudiant))) {
                evaluationDTO.setEvaRepondu(true);
            }
            return evaluationDTO;
        }).collect(Collectors.toSet());
        return ResponseEntity.ok(ApiResponse.ok(evaluationDTOs));
    }

    @PreAuthorize("hasAuthority('ETU')")
    @GetMapping("evaluations/last-year")
    public ResponseEntity<?> getEvaluationsForEnseignantLastYear(@AuthenticationPrincipal UserDetails currentUser) {
        Short enseignantId = authentificationService.getAuhtentification(currentUser.getUsername()).getNoEnseignant().getId();
        List<Evaluation> evaluations = evaluationService.getEvaluationsForEnseignantLastYear(Long.valueOf(enseignantId));
        List<EvaluationDTO> evaluationDTOs = evaluations.stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toList());
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
        Enseignant ens = authentificationService.getAuhtentification(currentUser.getUsername()).getNoEnseignant();
        evaluationService.saveEvaluation(evaluationDTO, ens);
        return ApiResponse.ok("Evaluation successfully created");
    }

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
    @PostMapping("update")
    public ResponseEntity<?> upadateEvaluation(@Validated @RequestBody EvaluationSaveDTO evaluationDTO, @AuthenticationPrincipal UserDetails currentUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("/n"));
            return ApiResponse.error("Error creating Evaluation: " + errorMessage);
        }
        Enseignant ens = authentificationService.getAuhtentification(currentUser.getUsername()).getNoEnseignant();
        evaluationService.updateEvaluationEns(evaluationDTO, ens);
        return ApiResponse.ok("Evaluation est mise à jour avec succès");
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @GetMapping("statistics/{id}")
    public ResponseEntity<?> getStatistics(@PathVariable Long id) {
        return ApiResponse.ok(evaluationService.getStatistics(id));
    }

}