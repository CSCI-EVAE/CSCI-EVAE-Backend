package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.ReponseEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReponseEvaluationMapper {
    @Mapping(source = "noEtudiant.noEtudiant", target = "noEtudiantNoEtudiant")
    @Mapping(source = "idEvaluation.id", target = "idEvaluationId")
    ReponseEvaluationDTO toDto(ReponseEvaluation reponseEvaluation);
}
