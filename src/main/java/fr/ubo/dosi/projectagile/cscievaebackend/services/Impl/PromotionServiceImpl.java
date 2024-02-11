package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> getPromotionById(String id) {
        return promotionRepository.findById(id);
    }

    @Override
    public Promotion updatePromotion(String id, Promotion updatedPromotion) throws ResourceNotFoundException {
        Optional<Promotion> existingPromotion = promotionRepository.findById(id);
        if (existingPromotion.isPresent()) {
            Promotion promotion = existingPromotion.get();
            // Update other properties as needed
            return promotionRepository.save(promotion);
        } else {
            throw new ResourceNotFoundException("Promotion not found with id: " + id);
        }
    }

    @Override
    public boolean deletePromotion(String id) throws ResourceNotFoundException {
        Optional<Promotion> existingPromotion = promotionRepository.findById(id);
        if (existingPromotion.isPresent()) {
            promotionRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException("Promotion not found with id: " + id);
        }
    }
}
