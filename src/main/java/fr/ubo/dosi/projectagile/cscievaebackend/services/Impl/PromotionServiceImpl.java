package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Promotion getPromotionById(PromotionId id) {
        return promotionRepository.findByPromotionId(id.getCodeFormation(), id.getAnneeUniversitaire());
    }
    @Override
    public List<Promotion> getAllPromotions() {
        if (promotionRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("la liste des promotions est vide");
        } else {
            return promotionRepository.findAll();
        }
    }
    @Override
    public Promotion findPromotionByAnneeUniversitaireAndCodeFormation(String anneeUniversitaire, String codeFormation) {
        return promotionRepository.findById_CodeFormationAndId_AnneeUniversitaire(codeFormation, anneeUniversitaire);
    }



}
