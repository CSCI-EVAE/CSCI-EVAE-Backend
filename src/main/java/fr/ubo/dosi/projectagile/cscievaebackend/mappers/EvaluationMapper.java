package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {


    @Mappings({
            @Mapping(target = "nomEnseignant", source = "noEnseignant.nom"),
            @Mapping(target = "codeFormation", source = "promotion.codeFormation.codeFormation"),
            @Mapping(target = "anneeUniversitaire", source = "promotion.id.anneeUniversitaire"),
            @Mapping(target = "noEvaluation", source = "noEvaluation"),
            @Mapping(target = "debutReponse", source = "debutReponse", qualifiedByName = "localDateToString"),
            @Mapping(target = "finReponse", source = "finReponse", qualifiedByName = "localDateToString")
    })
    EvaluationDTO evaluationToEvaluationDTO(Evaluation evaluation);

    @Named("localDateToString")
    default String localDateToString(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

}
