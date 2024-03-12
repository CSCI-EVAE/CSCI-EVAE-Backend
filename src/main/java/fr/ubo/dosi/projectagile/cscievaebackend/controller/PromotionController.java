package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.PromotionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.PromotionMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.FormationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.AuthentificationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private final AuthentificationServiceImpl as;
    private final PromotionMapper promotionMapper;
    private final  PromotionService promotionService;
    private final FormationRepository formationRepository;

    public PromotionController(AuthentificationServiceImpl as, PromotionMapper promotionMapper, PromotionService promotionService , FormationRepository formationRepository) {
        this.as = as;
        this.promotionMapper = promotionMapper;
        this.promotionService = promotionService;
        this.formationRepository = formationRepository;
    }

    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("PromotionsForENS")
    public ResponseEntity<?> getPromotionsForFormationAndYear(
            @AuthenticationPrincipal UserDetails currentUser) {

        Authentification auth = as.getAuhtentification(currentUser.getUsername());
        Set<Promotion> promotions = auth.getNoEnseignant().getPromotions();
        Set<PromotionDTO> PromotionDTOs = promotions.stream().map(promotionMapper::promotionToPromotionDTO).collect(Collectors.toSet());
        return ApiResponse.ok(PromotionDTOs);
    }
    @PreAuthorize("hasAuthority('ADM')")
    @GetMapping("/promotionsForADM")
    public ResponseEntity<?> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ApiResponse.ok(promotions);
    }
    // get all formation from a given promotion
    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("/formationsForPromotion/{codeformation}")
    public ResponseEntity<?> getFormationsForPromotion(@PathVariable String codeformation) {
        Formation formation = formationRepository.findById(codeformation).get();
        return ApiResponse.ok(formation.getPromotions().stream().map(promotionMapper::promotionToPromotionDTO).collect(Collectors.toSet()));
    }

}
