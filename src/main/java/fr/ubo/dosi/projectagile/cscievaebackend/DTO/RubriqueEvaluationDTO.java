package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<QuestionEvaluationDTO> questionEvaluations;
    public List<QuestionEvaluationDTO> getQuestionEvaluations() {
        return questionEvaluations.stream()
                .sorted(Comparator.comparing(QuestionEvaluationDTO::getOrdre))
                .collect(Collectors.toList());
    }
}
