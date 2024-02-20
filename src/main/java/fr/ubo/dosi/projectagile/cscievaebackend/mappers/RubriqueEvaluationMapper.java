package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RubriqueEvaluationMapper {
    RubriqueEvaluationDTO rubriqueEvaluationToRubriqueEvaluationDTO(RubriqueEvaluation rubriqueEvaluation);
    List<RubriqueEvaluationDTO> rubriqueEvaluationsToRubriqueEvaluationDTOs(List<RubriqueEvaluation> rubriqueEvaluations);
}