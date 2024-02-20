package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

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
    private Long idQuestion;
    @NotNull
    private Long idRubrique;
    @NotNull
    private String designation;
    @NotNull
    private Long ordre;
}
