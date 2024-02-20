package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueQuestionDTO {
    @NotNull
    private RubriqueDTO idRubrique;
    @NotNull
    private QuestionDTO idQuestion ;
    @NotNull
    private Long ordre;
}
