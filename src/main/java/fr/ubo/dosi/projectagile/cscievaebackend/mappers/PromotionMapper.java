package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.PromotionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;

public interface PromotionMapper {
    PromotionDTO promotionToPromotionDTO(Promotion promotion);
}
