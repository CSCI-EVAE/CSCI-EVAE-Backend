package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.ReponseEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;

import java.util.List;

public interface PromotionService {
    Promotion getPromotionById(PromotionId id);
     List<Promotion> getAllPromotions();
    Promotion findPromotionByAnneeUniversitaireAndCodeFormation(String anneeUniversitaire, String codeFormation);
}
