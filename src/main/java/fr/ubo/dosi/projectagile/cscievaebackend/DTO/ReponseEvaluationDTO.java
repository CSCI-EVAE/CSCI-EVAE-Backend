package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;

import java.util.LinkedHashSet;
import java.util.Set;

public class ReponseEvaluationDTO {
    private Integer id;
    private RubriqueDTO idRubrique;
    private Short ordre;
    Set<QuestionEvaluationDTO> questionEvaluations = new LinkedHashSet<>();
}
