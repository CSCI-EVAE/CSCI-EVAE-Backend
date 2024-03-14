package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.ReponseEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReponseEvaluationMapper {
    ReponseEvaluation toEntity(ReponseEvaluationDTO reponseEvaluation);
    ReponseEvaluationDTO toDto(ReponseEvaluation reponseEvaluation);
}
