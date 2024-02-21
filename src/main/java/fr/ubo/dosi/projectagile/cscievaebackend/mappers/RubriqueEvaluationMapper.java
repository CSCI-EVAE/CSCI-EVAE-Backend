package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {RubriqueMapper.class})
public interface RubriqueEvaluationMapper {

    RubriqueEvaluationDTO rubriqueEvaluationToRubriqueEvaluationDTO(RubriqueEvaluation rubriqueEvaluation);
}