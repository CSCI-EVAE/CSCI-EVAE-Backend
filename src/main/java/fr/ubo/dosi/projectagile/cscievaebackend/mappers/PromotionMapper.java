package fr.ubo.dosi.projectagile.cscievaebackend.mappers;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.PromotionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    @Mapping(source ="noEnseignant.nom" ,target ="nomResponsable")
    @Mapping(source ="noEnseignant.prenom" ,target ="prenomResponsable")
    @Mapping(source ="id.anneeUniversitaire" ,target ="anneeUniversitaire")
    @Mapping(source = "codeFormation.codeFormation", target = "codeFormation")
    PromotionDTO promotionToPromotionDTO(Promotion promotion);
}