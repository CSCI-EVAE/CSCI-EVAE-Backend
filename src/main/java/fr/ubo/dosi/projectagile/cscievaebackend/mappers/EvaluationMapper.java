package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EvaluationMapper {
    @Mapping(source = "noEnseignant.nom", target = "nomEnseignant")
    @Mapping(source = "noEnseignant.prenom", target = "prenomEnseignant")
    @Mapping(source = "promotion.id.anneeUniversitaire", target = "anneUniv")
    @Mapping(source = "promotion.id.codeFormation",target = "codeFormation")
    @Mapping(target = "nomEtudiant", ignore = true)
    @Mapping(target = "prenomEtudiant", ignore = true)
    @Mapping(target = "commentaire", ignore = true)
    @Mapping(target = "evaRepondu", ignore = true)
    @Mapping(source = "uniteEnseignement.id.codeUe", target = "codeUe")
    EvaluationDTO evaluationToEvaluationDTO(Evaluation evaluation);
}
