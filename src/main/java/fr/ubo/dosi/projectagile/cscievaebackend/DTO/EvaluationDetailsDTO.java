package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Getter
@Setter
public class EvaluationDetailsDTO {
    private Long noEnseignant;

    private Long elementConstitutif;

    private Set<RubriqueDTO> rubriqueEvaluations = new LinkedHashSet<>();

    private String promotion;

    private String etat;

    private String designation;

    private Integer IdUE;

    private String codeFormation;
}
