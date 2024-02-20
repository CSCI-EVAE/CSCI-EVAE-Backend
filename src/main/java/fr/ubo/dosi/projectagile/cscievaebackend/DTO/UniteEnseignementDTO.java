package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniteEnseignementDTO {
    private Integer evaluationId;
    private String etat;
    private LocalDate debutReponse;
    private LocalDate finReponse;
    private boolean EvaExiste;
    private String anneeUniversitaire;
    private String nomFormation;
    private String codeUe;
    private String codeEc;
    private String designation;
}
