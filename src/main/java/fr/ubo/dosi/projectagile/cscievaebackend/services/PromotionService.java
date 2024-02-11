package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionService {

    Promotion createPromotion(Promotion promotion);

    List<Promotion> getAllPromotions();

    Optional<Promotion> getPromotionById(String id);

    Promotion updatePromotion(String id, Promotion promotion) throws ResourceNotFoundException;

    boolean deletePromotion(String id) throws ResourceNotFoundException;
}
