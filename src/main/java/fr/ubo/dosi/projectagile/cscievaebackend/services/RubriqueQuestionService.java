package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;


import java.util.List;

public interface RubriqueQuestionService {
    List<Rubrique> findAllQuestionsForRubriques();
    String AjouterRubriqueQuestion(IncomingRubriqueQuestionDTO incomingData);

    String deleteAllRubriqueQuestion(Long id);
}
