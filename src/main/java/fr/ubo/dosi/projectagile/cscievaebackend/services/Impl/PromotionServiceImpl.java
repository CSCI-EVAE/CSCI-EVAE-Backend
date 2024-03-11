package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    Logger logger = Logger.getLogger(PromotionServiceImpl.class.getName());

    @Override
    public Promotion getPromotionById(PromotionId id) {
        return promotionRepository.findByPromotionId(id.getCodeFormation(), id.getAnneeUniversitaire());
    }

    @Override
    public Promotion findPromotionByAnneeUniversitaireAndCodeFormation(String anneeUniversitaire, String codeFormation) {
        return promotionRepository.findById_CodeFormationAndId_AnneeUniversitaire(codeFormation, anneeUniversitaire);
    }
}
