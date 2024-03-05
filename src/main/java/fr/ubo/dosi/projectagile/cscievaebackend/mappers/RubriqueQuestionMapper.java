package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RubriqueQuestionMapper {
    RubriqueQuestionDTO RubriqueQuestionToRubriqueQuestionDTO(RubriqueQuestion rubriqueQuestion);
}
