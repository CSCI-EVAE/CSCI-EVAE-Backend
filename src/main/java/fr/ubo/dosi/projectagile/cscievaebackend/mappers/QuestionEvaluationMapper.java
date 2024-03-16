package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionEvaluationMapper {

    @Mapping(target = "positionnement", ignore = true)
    @Mapping(target = "moyen", ignore = true)
    QuestionEvaluationDTO questionEvaluationToQuestionEvaluationDTO(QuestionEvaluation questionEvaluation);

}