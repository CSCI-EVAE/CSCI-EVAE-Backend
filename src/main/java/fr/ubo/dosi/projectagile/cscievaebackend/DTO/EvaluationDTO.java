package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationDTO  {
    private Integer id;

    private String designation;
    private LocalDate debutReponse;
    private LocalDate finReponse;
    private List<RubriqueEvaluationDTO> rubriqueEvaluations;
}
