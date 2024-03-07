package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {
    @Mapping(source = "noEnseignant.nom", target = "nomEnseignant")
    @Mapping(source = "noEnseignant.prenom", target = "prenomEnseignant")
    @Mapping(source = "promotion.id.anneeUniversitaire", target = "anneUniv")
    @Mapping(source = "promotion.id.codeFormation",target = "codeFormation")
    EvaluationDTO evaluationToEvaluationDTO(Evaluation evaluation);
}
