package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEvaluationDTO {
    private Integer id;
    private String intitule;
    private QuestionDTO idQuestion;
    private Short ordre;
    private QualificatifDTO idQualificatif;
    private Integer positionnement = 0;
    private double moyen = 0;
}
