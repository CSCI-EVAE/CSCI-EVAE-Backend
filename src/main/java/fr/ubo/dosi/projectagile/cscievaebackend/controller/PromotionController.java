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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Promotion", description = "Promotion API")
public class PromotionController {
    private final AuthentificationServiceImpl as;
    private final PromotionMapper promotionMapper;
    private final PromotionService promotionService;
    private final FormationRepository formationRepository;

    public PromotionController(AuthentificationServiceImpl as, PromotionMapper promotionMapper, PromotionService promotionService, FormationRepository formationRepository) {
        this.as = as;
        this.promotionMapper = promotionMapper;
        this.promotionService = promotionService;
        this.formationRepository = formationRepository;
    }

    @Operation(summary = "Get all Promotions", description = "Fetches all the Promotions from the database and maps them to DTOs")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Promotions fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PromotionDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Promotions", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("promotionsForENS")
    public ResponseEntity<?> getPromotions(
            @AuthenticationPrincipal UserDetails currentUser) {
        Authentification auth = as.getAuhtentification(currentUser.getUsername());
        Set<Promotion> promotions = auth.getNoEnseignant().getPromotions();
        Set<PromotionDTO> PromotionDTOs = promotions.stream().map(promotionMapper::promotionToPromotionDTO).collect(Collectors.toSet());
        return ApiResponse.ok(PromotionDTOs);
    }

    @Operation(summary = "Get all Promotions", description = "Fetches all the Promotions from the database and maps them to DTOs")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Promotions fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PromotionDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Promotions", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PreAuthorize("hasAuthority('ADM')")
    @GetMapping("/promotionsForADM")
    public ResponseEntity<?> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        Set<PromotionDTO> PromotionDTOs = promotions.stream().map(promotionMapper::promotionToPromotionDTO).collect(Collectors.toSet());
        return ApiResponse.ok(PromotionDTOs);
    }
    @Operation(summary = "Get all Promotions", description = "Fetches all the Promotions from the database and maps them to DTOs")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Promotions fetched successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PromotionDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "An error occurred while fetching the Promotions", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PreAuthorize("hasAuthority('ENS')")
    @GetMapping("/formationsForPromotion/{codeformation}")
    public ResponseEntity<?> getFormationsForPromotion(@PathVariable String codeformation) {
        Formation formation = formationRepository.findById(codeformation).get();
        return ApiResponse.ok(formation.getPromotions().stream().map(promotionMapper::promotionToPromotionDTO).collect(Collectors.toSet()));
    }

}
