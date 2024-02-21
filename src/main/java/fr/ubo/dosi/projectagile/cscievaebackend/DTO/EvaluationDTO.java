package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.Data;
import java.util.Set;


@Data
public class EvaluationDTO {
    private Integer id;
    private String codeFormation;
    private String anneeUniversitaire;
    private String nomEnseignant;
    private Integer noEvaluation;
    private String designation;
    private Set<RubriqueEvaluationDTO> rubriqueEvaluations;
    private String etat;
    private String periode;
    private String debutReponse;
    private String finReponse;
}


