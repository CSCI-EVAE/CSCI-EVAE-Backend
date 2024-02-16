package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;


import java.util.List;

public interface RubriqueQuestionService {
    public RubriqueQuestion saveRubriqueQuestion(RubriqueQuestion rubriqueQuestion);

    public void deleteRubriqueQuestion(RubriqueQuestion rubriqueQuestion);
    public List<RubriqueQuestionDTO> getAllRubriqueQuestions();
    public RubriqueQuestionDTO getRubriqueQuestionById(RubriqueQuestionId rubriqueQuestionId);
    public RubriqueQuestionDTO addRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionAddDTO);

}
