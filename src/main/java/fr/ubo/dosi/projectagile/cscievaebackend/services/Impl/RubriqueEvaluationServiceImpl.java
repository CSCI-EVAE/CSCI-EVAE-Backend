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
        RubriqueEvaluation rubriqueEvaluation =  rubriqueEvaluationRepository
                .findByIdRubriqueAndIdEvaluation(rubriqueQuestionDTO.getIdRubrique(), savedEvaluation) ;
        if (rubriqueEvaluation == null) {
            rubriqueEvaluation = new RubriqueEvaluation();
        }
        // Set common properties for both new and existing RubriqueEvaluations
        rubriqueEvaluation.setIdEvaluation(savedEvaluation);
        rubriqueEvaluation.setOrdre(rubriqueQuestionDTO.getOrdre().shortValue());
        try {
            rubriqueEvaluation.setIdRubrique(rubriqueService.getRubriqueById(rubriqueQuestionDTO.getIdRubrique()));
        } catch (Exception e) {
            throw new IllegalArgumentException("La rubrique n'existe pas", e);
        }

        // Clear existing QuestionEvaluations if RubriqueEvaluation already existed
        if (!rubriqueEvaluation.getQuestionEvaluations().isEmpty()) {
            rubriqueEvaluation.getQuestionEvaluations().clear();
        }

        // Create or update QuestionEvaluations
        RubriqueEvaluation finalRubriqueEvaluation = rubriqueEvaluation;
        rubriqueQuestionDTO.getQuestionIds().forEach((questionId, ordre) -> {
            QuestionEvaluation questionEvaluation = new QuestionEvaluation();
            try {
                questionEvaluation.setIdQuestion(questionService.getQuestionById(questionId));
                questionEvaluation.setOrdre(ordre.shortValue());
                questionEvaluation.setIdRubriqueEvaluation(finalRubriqueEvaluation);
                questionEvaluationService.saveQuestionEvaluation(questionEvaluation);
                finalRubriqueEvaluation.getQuestionEvaluations().add(questionEvaluation);
                logger.info("Question evaluation saved: " + questionEvaluation);
            } catch (Exception e) {
                throw new IllegalArgumentException("La question n'existe pas", e);
            }
        });

        rubriqueEvaluationRepository.save(rubriqueEvaluation);

        // Delete RubriqueEvaluations not present in DTO but existing in savedEvaluation
        savedEvaluation.getRubriqueEvaluations().removeIf(rubriqueEval ->
                !rubriqueQuestionDTO.getQuestionIds().containsKey(rubriqueEval.getIdRubrique().getId()));
        savedEvaluation.getRubriqueEvaluations().add(rubriqueEvaluation);
        logger.info("Rubrique evaluation saved: " + rubriqueEvaluation);
    }

    /*public void saveRubriqueEvaluation(IncomingRubriqueQuestionDTO rubriqueQuestionDTO, Evaluation savedEvaluation) {
        RubriqueEvaluation rubriqueEvaluation = new RubriqueEvaluation();
        if (rubriqueEvaluationRepository.existsByIdRubriqueAndIdEvaluation(rubriqueQuestionDTO.getIdRubrique(), savedEvaluation)) {
            rubriqueEvaluation = rubriqueEvaluationRepository.findByIdRubriqueAndIdEvaluation(rubriqueQuestionDTO.getIdRubrique(), savedEvaluation);
            rubriqueEvaluation.getQuestionEvaluations().clear();
        } else {
            try {
                rubriqueEvaluation.setIdRubrique(rubriqueService.getRubriqueById(rubriqueQuestionDTO.getIdRubrique()));
            } catch (Exception e) {
                throw new IllegalArgumentException("La rubrique n'existe pas");
            }
            rubriqueEvaluation.setIdEvaluation(savedEvaluation);
            rubriqueEvaluation.setOrdre(rubriqueQuestionDTO.getOrdre().shortValue());
            rubriqueEvaluation = rubriqueEvaluationRepository.save(rubriqueEvaluation);
        }
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
        // from the existing savedEvaluation.getRubriqueEvaluations() if an not in rubriqueQuestionDTO.getRubriqueIds() then delete it
        for (RubriqueEvaluation rubriqueEvaluation1 : savedEvaluation.getRubriqueEvaluations()) {
            if (!rubriqueQuestionDTO.getQuestionIds().containsKey(rubriqueEvaluation1.getIdRubrique().getIdRubrique())) {
                rubriqueEvaluationRepository.delete(rubriqueEvaluation1);
            }
        }
        logger.info("Rubrique evaluation saved: " + rubriqueEvaluation);
        savedEvaluation.getRubriqueEvaluations().add(rubriqueEvaluation);
    }*/

    @Transactional
    @Override
    public void saveRubriquesEvaluation(List<IncomingRubriqueQuestionDTO> rubriqueQuestion, Evaluation savedEvaluation) {
        if (rubriqueQuestion != null) {
            for (IncomingRubriqueQuestionDTO rubriqueQuestionDTO : rubriqueQuestion) {
                this.saveRubriqueEvaluation(rubriqueQuestionDTO, savedEvaluation);
            }
        }
    }
}
