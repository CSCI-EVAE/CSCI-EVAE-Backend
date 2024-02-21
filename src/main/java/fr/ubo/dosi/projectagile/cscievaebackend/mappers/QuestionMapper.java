package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO questionToQuestionDTO(Question question);


}
