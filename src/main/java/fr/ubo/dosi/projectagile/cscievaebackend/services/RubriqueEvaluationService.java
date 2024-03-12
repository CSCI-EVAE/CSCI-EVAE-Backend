package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import java.util.List;

public interface RubriqueEvaluationService {
    void saveRubriqueEvaluation(IncomingRubriqueQuestionDTO rubriqueQuestionDTO, Evaluation savedEvaluation);
    void saveRubriquesEvaluation(List<IncomingRubriqueQuestionDTO> rubriqueQuestion, Evaluation savedEvaluation);
}
