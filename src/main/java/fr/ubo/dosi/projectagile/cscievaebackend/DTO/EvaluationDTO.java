package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private String codeUe;
    private boolean evaRepondu=false;
    private String nomEtudiant = "";
    private String prenomEtudiant = "";
    private String commentaire = "";
    private Set<RubriqueEvaluationDTO> rubriqueEvaluations;
}

