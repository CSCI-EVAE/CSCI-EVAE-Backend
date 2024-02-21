package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {
    EvaluationDTO evaluationToEvaluationDTO(Evaluation evaluation);
    List<EvaluationDTO> evaluationsToEvaluationDTOs(List<Evaluation> evaluations);

}
