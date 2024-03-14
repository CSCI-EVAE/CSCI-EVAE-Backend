package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Short ordre;
    @Size(max = 64)
    private String intitule;
}