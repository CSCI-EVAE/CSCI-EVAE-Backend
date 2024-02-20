package fr.ubo.dosi.projectagile.cscievaebackend.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueQuestionDTO {


    private QuestionDTO idQuestion;
    private String designation;
    private Long ordre;
    @NotNull
    private Long idRubrique;

}
