package fr.ubo.dosi.projectagile.cscievaebackend.controller;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;

import java.util.Set;

@RestController
@RequestMapping("/api/promotions")
public class EtudiantController {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private EtudiantService etudiantService;

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
    @PutMapping("/update-etudiant/{noEtudiant}")
    @PreAuthorize("hasAuthority('ADM')")
    public ResponseEntity<?> updateEtudiant(@PathVariable("noEtudiant") String noEtudiant, @Validated @RequestBody EtudiantDTO etudiantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la mise à jour l'étudiant", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        }
        EtudiantDTO updatedEtudiant = etudiantService.updateEtudiant(noEtudiant, etudiantDTO);
        if (updatedEtudiant == null) {
            return ApiResponse.error("L'étudiant avec le numéro " + noEtudiant + " n'a pas été trouvé");
        }
        return ApiResponse.ok("Les informations de l'étudiant ont été mises à jour avec succès", updatedEtudiant);
    }
}
