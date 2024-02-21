package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.AuthentificationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.EvaluationServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {
    private final EvaluationServiceImpl es;
    private final AuthentificationServiceImpl as;
    private final EvaluationMapper evaluationMapper;
    private final ModelMapper modelMapper;
    private final EvaluationService evaluationService;
    Logger logger = Logger.getLogger(EvaluationController.class.getName());

    @Autowired
    public EvaluationController(EvaluationServiceImpl es, AuthentificationServiceImpl as, EvaluationMapper evaluationMapper, ModelMapper modelMapper, EvaluationService evaluationService) {
        this.es = es;
        this.as = as;
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
    public ApiResponse<?> getAll(@AuthenticationPrincipal UserDetails currentUser) {
        Authentification auth = as.getAuhtentification(currentUser.getUsername());
        Set<Evaluation> evaluations = auth.getNoEnseignant().getEvaluations();
        if (evaluations == null) {
            return ApiResponse.ok("Aucune évaluation n'est disponible pour cet enseignant");
        }
        Set<EvaluationDTO> evaluationDTOs = evaluations.stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toSet());
        return ApiResponse.ok(evaluationDTOs);
    }

    @PreAuthorize("hasAuthority('ENS')")
    @PutMapping("soumettre/{id}")
    public ApiResponse<EvaluationDTO> soumettreEvaluation(@PathVariable Long id) throws ResourceNotFoundException {
        try {

            EvaluationDTO updated = evaluationService.updateEvaluation(id);
            System.out.println("Checking security permissions...");
            return ApiResponse.ok(updated);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Evaluation not found");
        }
    }

    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @GetMapping("details/{Id}")
    public ApiResponse<EvaluationDTO> getDetails(@PathVariable Long Id) {
        try {
            EvaluationDTO evaluationDetails = es.getEvaluationById(Id);
            return ApiResponse.ok(evaluationDetails);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ApiResponse.error("Evaluation not found", null);
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
    public ApiResponse<Set<EvaluationDTO>> getEvaluationsByUser(@AuthenticationPrincipal UserDetails currentUser) {
        return ApiResponse.ok(as.getAuhtentification(currentUser.getUsername()).getNoEtudiant().getPromotion().getEvaluations().stream().map(evaluationMapper::evaluationToEvaluationDTO).collect(Collectors.toSet()));
    }

    @PreAuthorize("hasAuthority('ETU')")
    @GetMapping("evaluations/last-year")
    public ApiResponse<List<EvaluationDTO>> getEvaluationsForEnseignantLastYear(@AuthenticationPrincipal UserDetails currentUser) {
        Short enseignantId = as.getAuhtentification(currentUser.getUsername()).getNoEnseignant().getId();
        List<Evaluation> evaluations = es.getEvaluationsForEnseignantLastYear(Long.valueOf(enseignantId));
        List<EvaluationDTO> evaluationDTOs = evaluations.stream().map((element) -> modelMapper.map(element, EvaluationDTO.class)).collect(Collectors.toList());
        return ApiResponse.ok(evaluationDTOs);
    }

    //  creer une evaluation from Dto
    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    @PostMapping("create")
    public ApiResponse<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        Evaluation evaluation = modelMapper.map(evaluationDTO, Evaluation.class);
        logger.info("Evaluation to be saved: " + evaluation);
        Evaluation saved = es.createEvaluation(evaluation);
        return ApiResponse.ok(modelMapper.map(saved, EvaluationDTO.class));
    }


}