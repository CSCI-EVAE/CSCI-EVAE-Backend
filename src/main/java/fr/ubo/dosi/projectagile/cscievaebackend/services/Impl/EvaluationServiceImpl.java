package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.ElementConstitutifRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.UniteEnseignementRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository er;
    private final PromotionRepository promotionRepository;
    private final UniteEnseignementRepository uniteEnseignementRepository;
    private final ElementConstitutifRepository elementConstitutifRepository;
    private final EvaluationMapper evaluationMapper;
    private final RubriqueQuestionService rubriqueQuestionService;
    Logger logger = Logger.getLogger(EvaluationServiceImpl.class.getName());

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository er, PromotionRepository promotionRepository, UniteEnseignementRepository uniteEnseignementRepository, EvaluationMapper evaluationMapper, ElementConstitutifRepository elementConstitutifRepository, RubriqueQuestionService rubriqueQuestionService) {
        this.er = er;
        this.promotionRepository = promotionRepository;
        this.uniteEnseignementRepository = uniteEnseignementRepository;
        this.elementConstitutifRepository = elementConstitutifRepository;
        this.evaluationMapper = evaluationMapper;
        this.rubriqueQuestionService = rubriqueQuestionService;
    }


    public EvaluationDTO getEvaluationById(Long id) throws ChangeSetPersister.NotFoundException {
        Evaluation evaluation = er.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        logger.info("Evaluation found: " + evaluation);
        return evaluationMapper.evaluationToEvaluationDTO(evaluation);
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
            evaluation.setEtat("DIS");
            System.out.println("Evaluation to be saved: " + evaluation);
            er.save(evaluation);
            return evaluationMapper.evaluationToEvaluationDTO(evaluation);

        } else {
            throw new ResourceNotFoundException("L'evaluation n'existe pas avec cet id : " + id);
        }
    }

    @Override
    public Set<Evaluation> getEvaluationsByUser(Etudiant etudiant) {
        Promotion promotion = etudiant.getPromotion();
        logger.info("Promotion found: " + promotion);
        return promotion.getEvaluations();
    }

    @Override
    public String saveEvaluation(EvaluationSaveDTO evaluationDTO, Enseignant enseignant) {
        logger.info("Evaluation to be saved: " + evaluationDTO);
        StringBuilder resultMessage = new StringBuilder();
        Evaluation savedEvaluation = new Evaluation();
        if (er.existsByDesignation(evaluationDTO.getDesignation())) {
            throw new IllegalArgumentException("L'évaluation existe déjà");
        }
        savedEvaluation.setDesignation(evaluationDTO.getDesignation());
        savedEvaluation.setDebutReponse(evaluationDTO.getDebutReponse());
        savedEvaluation.setFinReponse(evaluationDTO.getFinReponse());
        savedEvaluation.setEtat("ELA");
        savedEvaluation.setNoEnseignant(enseignant);
        Promotion promotion = promotionRepository.findByPromotionId(evaluationDTO.getCodeFormation(), evaluationDTO.getAnneePro());
        if (promotion == null) {
            throw new NoSuchElementException("La promotion n'existe pas");
        }
        logger.info("Promotion found: " + promotion.getCodeFormation());
        savedEvaluation.setPromotion(promotion);
        UniteEnseignement uniteEnseignement = uniteEnseignementRepository.findById(evaluationDTO.getCodeUE(), evaluationDTO.getCodeFormation());
        if (uniteEnseignement == null) {
            throw new NoSuchElementException("L'unité d'enseignement n'existe pas");
        }
        savedEvaluation.setUniteEnseignement(uniteEnseignement);
        ElementConstitutif elementConstitutif = null;
        if (!Objects.equals(evaluationDTO.getCodeEC(), "")) {
            elementConstitutif = elementConstitutifRepository.findById(evaluationDTO.getCodeEC(), evaluationDTO.getCodeUE(), evaluationDTO.getCodeFormation());
            if (elementConstitutif == null) {
                throw new NoSuchElementException("L'élément constitutif n'existe pas");
            }
        }
        savedEvaluation.setElementConstitutif(elementConstitutif);
        savedEvaluation.setNoEvaluation((short) (Math.random() * 100));
        savedEvaluation.setPeriode(evaluationDTO.getPeriode());
        for (IncomingRubriqueQuestionDTO inDto : evaluationDTO.getRubriqueQuestion()) {
            resultMessage.append(rubriqueQuestionService.AjouterRubriqueQuestion(inDto));
        }
        logger.info("Evaluation to be saved: " + evaluationMapper.evaluationToEvaluationDTO(savedEvaluation));
        er.save(savedEvaluation);
        return resultMessage.toString();
    }
}
