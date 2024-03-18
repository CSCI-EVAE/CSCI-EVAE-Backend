package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueEvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RubriqueEvaluationServiceImpl implements RubriqueEvaluationService {
    private final RubriqueEvaluationRepository rubriqueEvaluationRepository;
    private final RubriqueService rubriqueService;
    private final QuestionService questionService;
    private final QuestionEvaluationService questionEvaluationService;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public RubriqueEvaluationServiceImpl(RubriqueEvaluationRepository rubriqueEvaluationRepository, RubriqueService rubriqueService, QuestionService questionService, QuestionEvaluationService questionEvaluationService,
                                         EvaluationRepository evaluationRepository) {
        this.rubriqueEvaluationRepository = rubriqueEvaluationRepository;
        this.rubriqueService = rubriqueService;
        this.questionService = questionService;
        this.questionEvaluationService = questionEvaluationService;
        this.evaluationRepository = evaluationRepository;
    }


    @Transactional
    @Override
    public void saveRubriqueEvaluation(IncomingRubriqueQuestionDTO rubriqueQuestionDTO, Evaluation savedEvaluation) {
        RubriqueEvaluation rubriqueEvaluation = rubriqueEvaluationRepository
                .findByIdRubriqueAndIdEvaluation(rubriqueQuestionDTO.getIdRubrique(), savedEvaluation.getNoEvaluation().longValue()).orElse(new RubriqueEvaluation());

        rubriqueEvaluation.setIdEvaluation(savedEvaluation);
        rubriqueEvaluation.setOrdre(rubriqueQuestionDTO.getOrdre().shortValue());
        try {
            Rubrique rubrique = rubriqueService.getRubriqueById(rubriqueQuestionDTO.getIdRubrique());
            rubriqueEvaluation.setIdRubrique(rubrique);
            Logger.getLogger(RubriqueEvaluationServiceImpl.class.getName()).info("Rubrique found: " + rubrique);
        } catch (Exception e) {
            throw new IllegalArgumentException("La rubrique n'existe pas", e);
        }

        if (!rubriqueEvaluation.getQuestionEvaluations().isEmpty()) {
            rubriqueEvaluation.getQuestionEvaluations().forEach(questionEvaluationService::deleteQuestionEvaluation);
            rubriqueEvaluation.getQuestionEvaluations().clear();
        }

        rubriqueQuestionDTO.getQuestionIds().forEach((questionId, ordre) -> {
            QuestionEvaluation questionEvaluation = new QuestionEvaluation();
            try {
                questionEvaluation.setIdQuestion(questionService.getQuestionById(questionId));
                questionEvaluation.setOrdre(ordre.shortValue());
            } catch (Exception e) {
                throw new IllegalArgumentException("La question n'existe pas ", e);
            }
            rubriqueEvaluationRepository.save(rubriqueEvaluation);
            questionEvaluation.setIdRubriqueEvaluation(rubriqueEvaluation);
            questionEvaluationService.saveQuestionEvaluation(questionEvaluation);
            rubriqueEvaluation.getQuestionEvaluations().add(questionEvaluation);
        });

        rubriqueEvaluationRepository.save(rubriqueEvaluation);
        savedEvaluation.getRubriqueEvaluations().add(rubriqueEvaluation);
    }

    @Transactional
    @Override
    public void saveRubriquesEvaluation(List<IncomingRubriqueQuestionDTO> rubriqueQuestion, Evaluation savedEvaluation) {
        if (rubriqueQuestion != null) {
            for (IncomingRubriqueQuestionDTO rubriqueQuestionDTO : rubriqueQuestion) {
                this.saveRubriqueEvaluation(rubriqueQuestionDTO, savedEvaluation);
            }
        }
        evaluationRepository.save(savedEvaluation);
    }

}
