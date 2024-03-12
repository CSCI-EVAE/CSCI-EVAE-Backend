package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UniteEnseignementDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UniteEnseignementDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.AuthentificationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.AuthentificationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.UniteEnseignementServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/enseignant/ue")
public class UniteEnseignementController {

    private final userService userService;
    private final Logger logger = Logger.getLogger(UniteEnseignementController.class.getName());

    @Autowired
    public UniteEnseignementController(userService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> getAllUE(@AuthenticationPrincipal UserDetails currentUser) {
        Authentification auth = userService.getUserByUsername(currentUser.getUsername());
        Set<UniteEnseignement> uniteEnseignements = auth.getNoEnseignant().getUniteEnseignements();
        if (uniteEnseignements.isEmpty()) {
            return ApiResponse.error("Aucune UE trouvée");
        }
        logger.info("uniteEnseignements : " + uniteEnseignements);
        List<UniteEnseignementDTO> ues = uniteEnseignements.stream().map(ue -> {
            UniteEnseignementDTO uniteEnseignementDTO = new UniteEnseignementDTO();
            uniteEnseignementDTO.setCodeUe(ue.getId().getCodeUe());
            uniteEnseignementDTO.setDesignation(ue.getDesignation());
            if (ue.getEvaluations().isEmpty()) {
                uniteEnseignementDTO.setEvaExiste(null);
            }else {
                uniteEnseignementDTO.setEvaExiste(ue.getEvaluations().stream().findFirst().get());
            }
            uniteEnseignementDTO.setNbhCm(ue.getNbhCm());
            uniteEnseignementDTO.setNbhTd(ue.getNbhTd());
            uniteEnseignementDTO.setNbhTp(ue.getNbhTp());
            uniteEnseignementDTO.setTotalHeures();
            if (ue.getFormation() != null) {
                uniteEnseignementDTO.setCodeFormation(ue.getFormation().getCodeFormation());
                uniteEnseignementDTO.setNomFormation(ue.getFormation().getNomFormation());
            }
            return uniteEnseignementDTO;
        }).toList();
        logger.info("ues : " + ues);
        return ApiResponse.ok(ues);
    }
}
