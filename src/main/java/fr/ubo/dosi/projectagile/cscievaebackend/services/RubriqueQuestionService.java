package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionsDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;


import java.util.List;

public interface RubriqueQuestionService {
    public List<RubriqueQuestionsDTO> findAllQuestionsForRubriques() ;
    public RubriqueQuestion saveRubriqueQuestion(RubriqueQuestion rubriqueQuestion);

    public void deleteRubriqueQuestion(RubriqueQuestion rubriqueQuestion);
    public RubriqueQuestionDTO getRubriqueQuestionById(RubriqueQuestionId rubriqueQuestionId);
    String processAndStore(List<IncomingRubriqueQuestionDTO> incomingData);

    String AjouterRubriqueQuestion(IncomingRubriqueQuestionDTO incomingData);
}
