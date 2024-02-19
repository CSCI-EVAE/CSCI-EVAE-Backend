package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.AuthentificationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.EvaluationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/evaluation")
//@CrossOrigin(origins = "http://localhost:3000")
public class EvaluationController {

    @Autowired
    private EvaluationServiceImpl es;

    @Autowired
    private AuthentificationServiceImpl as;
    @Autowired
    private EvaluationMapper evaluationMapper;

    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("getAll")
    public ApiResponse<Set<Evaluation>> getAll(@AuthenticationPrincipal UserDetails currentUser){

        return ApiResponse.ok(as.getAuhtentification(currentUser.getUsername()).getNoEnseignant().getEvaluations());

    }

    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("details/{Id}")
    public ApiResponse<EvaluationDTO> getDetails (@PathVariable Long Id, @AuthenticationPrincipal UserDetails currentUser ){
    // Todo: On doit s'assurer que l'évaluation appartient à l'enseignant connecté
        EvaluationDTO evaluationDetails = evaluationMapper.evaluationToEvaluationDTO(es.getEvaluationById(Id));
        return ApiResponse.ok(evaluationDetails);
    }
}