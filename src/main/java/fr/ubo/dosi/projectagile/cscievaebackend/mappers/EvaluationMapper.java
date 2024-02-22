package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {


    EvaluationDTO evaluationToEvaluationDTO(Evaluation evaluation);

    Evaluation evaluationDTOToEvaluation(EvaluationDTO evaluationDTO);
}
