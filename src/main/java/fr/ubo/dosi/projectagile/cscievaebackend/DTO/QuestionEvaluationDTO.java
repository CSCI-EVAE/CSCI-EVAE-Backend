package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionEvaluationDTO {
    private Integer id;
    @NotNull
    @Size(max = 64)
    private String intitule;
    @NotNull
    private Short ordre;
}
