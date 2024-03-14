package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionEvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionEvaluationServiceImpl implements QuestionEvaluationService {
    @Autowired
    public QuestionEvaluationRepository questionEvaluationRepository;
    @Override
    public QuestionEvaluation saveQuestionEvaluation(QuestionEvaluation questionEvaluation) {
       return questionEvaluationRepository.save(questionEvaluation);
    }

    @Override
    public void deleteQuestionEvaluation(QuestionEvaluation questionEvaluation) {
        questionEvaluationRepository.delete(questionEvaluation);
    }
}
