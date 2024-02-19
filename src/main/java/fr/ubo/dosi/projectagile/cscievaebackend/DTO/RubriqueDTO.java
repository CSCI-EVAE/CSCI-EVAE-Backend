package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
@Data
public class RubriqueDTO {
    private Long idRubrique;
    private String type;
    private String designation;
    private Long ordre;
}
