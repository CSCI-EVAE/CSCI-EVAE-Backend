package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueEvaluationDTO {
    private Integer id;
    @NotNull
    @Size(max = 64)
    private String designation;
    private RubriqueDTO idRubrique;
    @NotNull
    private Short ordre;
    private List<QuestionEvaluationDTO> questionEvaluations;
}
