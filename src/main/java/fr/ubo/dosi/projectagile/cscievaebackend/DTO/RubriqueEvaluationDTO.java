package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueEvaluationDTO {
    private Integer id;
    private String designation;
    private RubriqueDTO idRubrique;
    private Short ordre;

    private List<QuestionEvaluationDTO> questionEvaluations;
}
