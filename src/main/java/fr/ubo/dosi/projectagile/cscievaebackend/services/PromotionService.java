package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;

public interface PromotionService {
    Promotion getPromotionById(PromotionId id);
}
