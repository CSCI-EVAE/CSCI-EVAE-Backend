package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationDTO {
    private Integer id;
    private String etat;
    private String codeFormation;
    private String anneUniv;
    private String prenomEnseignant;
    private String nomEnseignant;
    private String designation;
    private Short noEvaluation;
    private LocalDate debutReponse;
    private LocalDate finReponse;
    private String periode;
    private Set<RubriqueEvaluationDTO> rubriqueEvaluations;
    private ElementConstitutif elementConstitutif;
}

