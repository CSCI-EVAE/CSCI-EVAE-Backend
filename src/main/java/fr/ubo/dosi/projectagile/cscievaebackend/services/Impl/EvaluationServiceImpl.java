package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveOrUpdateDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.*;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationRepository er;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private RubriqueEvaluationRepository rubriqueEvaluationRepository;

    @Autowired
    private QuestionEvaluationRepository questionEvaluationRepository;

    @Autowired
    private EvaluationMapper evaluationMapper;

    public List<Evaluation> getAll() {
        try {
            return er.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Evaluation getEvaluationById(Long id) {
        Optional<Evaluation> evaluation = er.findById(id);
        return evaluation.orElseThrow(() -> new NoSuchElementException("Aucune évaluation avec l'id " + id + " n'a été trouvée"));
    }

public ApiResponse<String> saveOrUpdateEvaluation(EvaluationSaveOrUpdateDTO evaluationDTO) {
    Evaluation newEvaluation = new Evaluation();
    newEvaluation.setDesignation(evaluationDTO.getDesignation());
    newEvaluation.setDebutReponse(evaluationDTO.getDebutReponse());
    newEvaluation.setFinReponse(evaluationDTO.getFinReponse());
    newEvaluation.setPeriode(evaluationDTO.getPeriode());
    newEvaluation.setEtat("ELA");
    newEvaluation.setNoEvaluation(evaluationDTO.getNoEvaluation());
    evaluationRepository.save(newEvaluation);
    if (evaluationDTO.getRubriqueEvaluations() != null && !evaluationDTO.getRubriqueEvaluations().isEmpty()) {
        for (RubriqueEvaluationDTO rubriqueDTO : evaluationDTO.getRubriqueEvaluations()) {
            RubriqueEvaluation rubriqueEvaluation = new RubriqueEvaluation();
            rubriqueEvaluationRepository.save(rubriqueEvaluation);
        }
    }

    if (evaluationDTO.getQuestionEvaluations() != null && !evaluationDTO.getQuestionEvaluations().isEmpty()) {
        for (QuestionEvaluationDTO questionDTO : evaluationDTO.getQuestionEvaluations()) {
            QuestionEvaluation questionEvaluation = new QuestionEvaluation();
            questionEvaluationRepository.save(questionEvaluation);
        }
    }
    return ApiResponse.ok("L'évaluation a été enregistrée avec succès");
}

    @Override
    public List<Evaluation> getEvaluationsForEnseignantLastYear(Long enseignantId) {

        String lastYear = String.valueOf(LocalDate.now().getYear() - 1);
        return er.findAllByEnseignantAndLastYear(enseignantId, lastYear);
    }

    @Override
    public EvaluationDTO updateEvaluation(Long id) throws ResourceNotFoundException {
        Optional<Evaluation> evaluationExistant = er.findById(id);

        if (evaluationExistant.isPresent()) {
            Evaluation evaluation = evaluationExistant.get();
            System.out.println("je suis :"+evaluation.getEtat());
            evaluation.setEtat("DIS");
            System.out.println("je suis :"+evaluation.getEtat());
            System.out.println("Evaluation to be saved: " + evaluation);
            er.save(evaluation);
            return evaluationMapper.evaluationToEvaluationDTO(evaluation);

        } else {
            throw new ResourceNotFoundException("L'evaluation n'existe pas avec cet id : " + id);
        }
    }


}
