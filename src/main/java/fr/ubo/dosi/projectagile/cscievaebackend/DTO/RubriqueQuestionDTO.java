package fr.ubo.dosi.projectagile.cscievaebackend.DTO;
import lombok.*;

@Data
public class RubriqueQuestionDTO {
    private Long ordre;
    private RubriqueDTO idRubrique;
    private QuestionDTO idQuestion;
}
