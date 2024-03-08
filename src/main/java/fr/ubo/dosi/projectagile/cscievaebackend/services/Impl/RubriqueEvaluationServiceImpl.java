package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueEvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RubriqueEvaluationServiceImpl implements RubriqueEvaluationService {
    private final RubriqueEvaluationRepository rubriqueEvaluationRepository;
    private final RubriqueService rubriqueService;
    private final QuestionService questionService;
    private final QuestionEvaluationService questionEvaluationService;

    @Autowired
    public RubriqueEvaluationServiceImpl(RubriqueEvaluationRepository rubriqueEvaluationRepository, RubriqueService rubriqueService, QuestionService questionService, QuestionEvaluationService questionEvaluationService) {
        this.rubriqueEvaluationRepository = rubriqueEvaluationRepository;
        this.rubriqueService = rubriqueService;
        this.questionService = questionService;
        this.questionEvaluationService = questionEvaluationService;
    }

    @Override
    public void saveRubriqueEvaluation(IncomingRubriqueQuestionDTO rubriqueQuestionDTO, Evaluation savedEvaluation) {
        RubriqueEvaluation rubriqueEvaluation = new RubriqueEvaluation();
        try {
            rubriqueEvaluation.setIdRubrique(rubriqueService.getRubriqueById(rubriqueQuestionDTO.getIdRubrique()));
        } catch (Exception e) {
            throw new IllegalArgumentException("La rubrique n'existe pas");
        }
        rubriqueEvaluation.setIdEvaluation(savedEvaluation);
        rubriqueEvaluation.setOrdre(rubriqueQuestionDTO.getOrdre().shortValue());
        for (Map.Entry<Long, Long> entry : rubriqueQuestionDTO.getQuestionIds().entrySet()) {
            QuestionEvaluation questionEvaluation = new QuestionEvaluation();
            try {
                questionEvaluation.setIdQuestion(questionService.getQuestionById(entry.getKey()));
            } catch (Exception e) {
                throw new IllegalArgumentException("La question n'existe pas");
            }
            questionEvaluation.setOrdre(entry.getValue().shortValue());
            questionEvaluation.setIdRubriqueEvaluation(rubriqueEvaluation);
            questionEvaluationService.saveQuestionEvaluation(questionEvaluation);
            rubriqueEvaluation.getQuestionEvaluations().add(questionEvaluation);
        }
        rubriqueEvaluationRepository.save(rubriqueEvaluation);
    }
}
