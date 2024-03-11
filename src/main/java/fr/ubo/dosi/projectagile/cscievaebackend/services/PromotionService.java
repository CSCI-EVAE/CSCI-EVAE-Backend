package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;

import java.util.List;
import java.util.Set;

public interface PromotionService {
    Promotion getPromotionById(PromotionId id);

    Promotion findPromotionByAnneeUniversitaireAndCodeFormation(String anneeUniversitaire, String codeFormation);
}