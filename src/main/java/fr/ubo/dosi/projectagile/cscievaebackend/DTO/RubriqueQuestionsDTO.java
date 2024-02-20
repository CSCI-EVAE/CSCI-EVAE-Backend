package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.Data;

import java.util.List;


@Data
public class RubriqueQuestionsDTO {
    private RubriqueDTO idRubrique;
    private List<QuestionOrdreDTO>  QuestionsOrdre;
}
