package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionEvaluationMapper {
    QuestionEvaluationDTO questionEvaluationToQuestionEvaluationDTO(QuestionEvaluation questionEvaluation);

}