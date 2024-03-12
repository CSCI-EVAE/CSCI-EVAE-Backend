package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueEvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class RubriqueEvaluationServiceImpl implements RubriqueEvaluationService {
    private final RubriqueEvaluationRepository rubriqueEvaluationRepository;
    private final RubriqueService rubriqueService;
    private final QuestionService questionService;
    private final QuestionEvaluationService questionEvaluationService;
    Logger logger = Logger.getLogger(RubriqueEvaluationServiceImpl.class.getName());

    @Autowired
    public RubriqueEvaluationServiceImpl(RubriqueEvaluationRepository rubriqueEvaluationRepository, RubriqueService rubriqueService, QuestionService questionService, QuestionEvaluationService questionEvaluationService) {
        this.rubriqueEvaluationRepository = rubriqueEvaluationRepository;
        this.rubriqueService = rubriqueService;
        this.questionService = questionService;
        this.questionEvaluationService = questionEvaluationService;
    }


    @Transactional
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
        rubriqueEvaluation = rubriqueEvaluationRepository.save(rubriqueEvaluation);
        for (Map.Entry<Long, Long> entry : rubriqueQuestionDTO.getQuestionIds().entrySet()) {
            QuestionEvaluation questionEvaluation = new QuestionEvaluation();
            try {
                questionEvaluation.setIdQuestion(questionService.getQuestionById(entry.getKey()));
            } catch (Exception e) {
                throw new IllegalArgumentException("La question n'existe pas");
            }
            questionEvaluation.setOrdre(entry.getValue().shortValue());
            questionEvaluation.setIdRubriqueEvaluation(rubriqueEvaluation);
            questionEvaluation = questionEvaluationService.saveQuestionEvaluation(questionEvaluation);
            logger.info("Question evaluation saved: " + questionEvaluation);
            rubriqueEvaluation.getQuestionEvaluations().add(questionEvaluation);
        }
        rubriqueEvaluationRepository.save(rubriqueEvaluation);
        logger.info("Rubrique evaluation saved: " + rubriqueEvaluation);
        savedEvaluation.getRubriqueEvaluations().add(rubriqueEvaluation);
    }

    @Transactional
    @Override
    public void saveRubriquesEvaluation(List<IncomingRubriqueQuestionDTO> rubriqueQuestion, Evaluation savedEvaluation) {
        if (rubriqueQuestion!= null) {
            for (IncomingRubriqueQuestionDTO rubriqueQuestionDTO : rubriqueQuestion) {
                this.saveRubriqueEvaluation(rubriqueQuestionDTO, savedEvaluation);
            }
        }
    }
}
