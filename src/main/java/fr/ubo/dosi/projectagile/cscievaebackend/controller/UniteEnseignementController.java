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
import fr.ubo.dosi.projectagile.cscievaebackend.services.UniteEnseignementService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/enseignant/ue")
public class UniteEnseignementController {

    private final userService userService;
    private final UniteEnseignementService uniteEnseignementService;
    private final Logger logger = Logger.getLogger(UniteEnseignementController.class.getName());

    @Autowired
    public UniteEnseignementController(userService userService, UniteEnseignementService uniteEnseignementService) {
        this.userService = userService;
        this.uniteEnseignementService = uniteEnseignementService;
    }

    @PreAuthorize("hasAuthority('ENS')")
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
                }).sorted(etatComparator)
                .collect(Collectors.toList());
        return ApiResponse.ok(ues);
    }

    Comparator<UniteEnseignementDTO> etatComparator = (ue1, ue2) -> {
        List<String> order = Arrays.asList(null, "ELA", "DIS", "CLO");
        if (ue1.getEtat() == null) {
            return (ue2.getEtat() == null) ? 0 : -1;
        }
        if (ue2.getEtat() == null) {
            return 1;
        }
        return Integer.compare(order.indexOf(ue1.getEtat()), order.indexOf(ue2.getEtat()));
    };

    @GetMapping("/promotion/{codeFormation}")
    ResponseEntity<?> getAllUEByPromotions(@PathVariable String codeFormation) {
        return ApiResponse.ok(uniteEnseignementService.getAllUEByPromotions(codeFormation));
    }
}
