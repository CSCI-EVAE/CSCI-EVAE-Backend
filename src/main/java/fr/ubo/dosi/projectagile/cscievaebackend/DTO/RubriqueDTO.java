package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;

import java.util.LinkedHashSet;
import java.util.Set;

public class RubriqueDTO {
    private Integer id;

    private String type;

    private String designation;
    private Short ordre;
    private Set<QuestionDTO> questionEvaluations = new LinkedHashSet<>();


}
