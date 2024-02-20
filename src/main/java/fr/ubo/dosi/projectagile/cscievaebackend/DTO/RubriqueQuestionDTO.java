package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueQuestionDTO {
    @NotNull
    private QuestionDTO idQuestion;
    private String designation;
    @NotNull
    private Long idRubrique;

}
