package fr.ubo.dosi.projectagile.cscievaebackend.model;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link QuestionEvaluation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEvaluationDto implements Serializable {
    private Integer id;
    private Short ordre;
    @Size(max = 64)
    private String intitule;
    private QuestionDTO idQuestion;
}