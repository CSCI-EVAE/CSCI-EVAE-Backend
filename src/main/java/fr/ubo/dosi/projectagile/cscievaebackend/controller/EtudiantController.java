package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.ReponseEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.services.AuthentificationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/etudiant")
public class EtudiantController {

    private final PromotionService promotionService;
    private final EvaluationService evaluationService;
    private final EtudiantService etudiantService;
    private final AuthentificationService authentificationService;

    public EtudiantController(AuthentificationService authentificationService, PromotionService promotionService, EvaluationService evaluationService, EtudiantService etudiantService) {
        this.authentificationService = authentificationService;
        this.promotionService = promotionService;
        this.evaluationService = evaluationService;
        this.etudiantService = etudiantService;
    }

    @GetMapping("/{anneeUniversitaire}/{codeFormation}/etudiants")
    @PreAuthorize("hasAuthority('ADM') or hasAuthority('ENS')")
    public ResponseEntity<?> getEtudiantsByPromotionAndFormation(@PathVariable String anneeUniversitaire, @PathVariable String codeFormation) {
        Promotion promotion = promotionService.findPromotionByAnneeUniversitaireAndCodeFormation(anneeUniversitaire, codeFormation);
        if (promotion == null) {
            return ApiResponse.error("Cette promotion n'existe pas");
        }
        Set<Etudiant> etudiants = promotion.getEtudiants();
        return ApiResponse.ok(etudiants);
    }

    @DeleteMapping("/etudiants/{noEtudiant}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> deleteEtudiant(@PathVariable String noEtudiant) {
        etudiantService.deleteEtudiant(noEtudiant);
        return ApiResponse.ok("Etudiant supprimé");
    }

    @PostMapping("reponduEvaluation")
    public ResponseEntity<?> setReponseEtudiant(@RequestBody ReponseEvaluationDTO reponseEvaluationDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etu = authentificationService.getAuhtentification(userDetails.getUsername()).getNoEtudiant();
        return ApiResponse.ok(evaluationService.saveReponseEtudiant(reponseEvaluationDTO, etu));
    }

    @DeleteMapping("deleteReponse/{id}")
    public ResponseEntity<?> deleteReponseEtudiant(@PathVariable Integer id) {
        return ApiResponse.ok(evaluationService.deleteReponse(id));
    }

    @GetMapping("getReponses/{id}")
    public ResponseEntity<?> getReponsesEtudiant(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        Etudiant etu = authentificationService.getAuhtentification(userDetails.getUsername()).getNoEtudiant();
        return ApiResponse.ok(evaluationService.getReponsesEtudiant(id, etu));
    }

}
