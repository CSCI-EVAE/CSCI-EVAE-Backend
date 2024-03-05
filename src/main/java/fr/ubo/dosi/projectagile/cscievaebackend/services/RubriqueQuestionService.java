package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionsDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;


import java.util.List;

public interface RubriqueQuestionService {
    List<Rubrique> findAllQuestionsForRubriques();

    RubriqueQuestion saveRubriqueQuestion(RubriqueQuestion rubriqueQuestion);

    void deleteRubriqueQuestion(RubriqueQuestion rubriqueQuestion);

    RubriqueQuestionDTO getRubriqueQuestionById(RubriqueQuestionId rubriqueQuestionId);

    String processAndStore(List<IncomingRubriqueQuestionDTO> incomingData);

    String AjouterRubriqueQuestion(IncomingRubriqueQuestionDTO incomingData);

    String updateRubriqueQuestions(IncomingRubriqueQuestionDTO incomingData);

    String deleteAllRubriqueQuestion(Long id);
}
