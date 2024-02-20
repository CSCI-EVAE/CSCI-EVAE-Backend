package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionEvaluationDTO {
    private Integer id;
    @NotNull
    @Size(max = 64)
    private String intitule;
    @NotNull
    private Question idQuestion;
    private Short ordre;
}
