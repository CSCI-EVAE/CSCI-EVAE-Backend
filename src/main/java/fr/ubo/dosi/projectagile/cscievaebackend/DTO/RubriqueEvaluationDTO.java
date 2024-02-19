package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RubriqueEvaluationDTO {
    private Integer id;
    private String designation;
    private Short ordre;
    private List<QuestionEvaluationDTO> questionEvaluations;
}
