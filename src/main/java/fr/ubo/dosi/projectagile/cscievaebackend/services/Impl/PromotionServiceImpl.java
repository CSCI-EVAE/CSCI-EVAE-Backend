package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    @Override
    public Promotion getPromotionById(PromotionId id) {
        return promotionRepository.findByPromotionId(id.getCodeFormation(), id.getAnneeUniversitaire());
    }
}
