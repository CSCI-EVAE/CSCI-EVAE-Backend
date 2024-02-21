package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationDTO  {
    private Integer id;
    private String etat;
    private String designation;
    private LocalDate debutReponse;
    private LocalDate finReponse;
    private Set<RubriqueEvaluationDTO> rubriqueEvaluations;
}

