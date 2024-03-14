package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;

public interface QuestionEvaluationService {
    QuestionEvaluation saveQuestionEvaluation(QuestionEvaluation questionEvaluation);

    void deleteQuestionEvaluation(QuestionEvaluation questionEvaluation);
}
