package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionEvaluationDTO {
    private Integer id;
    private String intitule;
    private Question idQuestion;

    private Short ordre;
}
