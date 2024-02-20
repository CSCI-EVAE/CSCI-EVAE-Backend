package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueDTO {
    private Integer id;
    @NotNull
    @Size(max = 10)
    private String type;
    @NotNull
    @Size(max = 32)
    private String designation;
    private Short ordre;
    private Set<QuestionDTO> questionEvaluations = new LinkedHashSet<>();


}
