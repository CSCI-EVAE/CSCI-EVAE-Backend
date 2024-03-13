package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueEvaluationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private final RubriqueEvaluationRepository rubriqueEvaluationRepository;
    private final DroitRepository droitRepository;
    private final ReponseQuestionRepository reponseQuestionRepository;

    private final QuestionEvaluationRepository questionEvaluationRepository;
    private final ReponseEvaluationRepository reponseEvaluationRepository;
    private final RubriqueEvaluationService rubriqueEvaluationService;
    Logger logger = Logger.getLogger(EvaluationServiceImpl.class.getName());
    private final EtudiantRepository etudiantRepository;

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository er, PromotionRepository promotionRepository, UniteEnseignementRepository uniteEnseignementRepository, EvaluationMapper evaluationMapper, ElementConstitutifRepository elementConstitutifRepository, RubriqueEvaluationRepository rubriqueEvaluationRepository, DroitRepository droitRepository, ReponseQuestionRepository reponseQuestionRepository, QuestionEvaluationRepository questionEvaluationRepository, ReponseEvaluationRepository reponseEvaluationRepository, RubriqueEvaluationService rubriqueQuestionService,
                                 EtudiantRepository etudiantRepository) {
        this.er = er;
        this.promotionRepository = promotionRepository;
        this.uniteEnseignementRepository = uniteEnseignementRepository;
        this.elementConstitutifRepository = elementConstitutifRepository;
        this.evaluationMapper = evaluationMapper;
        this.rubriqueEvaluationRepository = rubriqueEvaluationRepository;
        this.droitRepository = droitRepository;
        this.reponseQuestionRepository = reponseQuestionRepository;
        this.questionEvaluationRepository = questionEvaluationRepository;
        this.reponseEvaluationRepository = reponseEvaluationRepository;
        this.rubriqueEvaluationService = rubriqueQuestionService;
        this.etudiantRepository = etudiantRepository;
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

    @Transactional
    @Override
    public void saveEvaluation(EvaluationSaveDTO evaluationDTO, Enseignant enseignant) {
        Evaluation savedEvaluation = new Evaluation();
        if (er.existsByDesignation(evaluationDTO.getDesignation())) {
            throw new IllegalArgumentException("L'évaluation existe déjà avec cette désignation");
        }
        savedEvaluation.setDesignation(evaluationDTO.getDesignation());
        savedEvaluation.setDebutReponse(evaluationDTO.getDebutReponse());
        savedEvaluation.setFinReponse(evaluationDTO.getFinReponse());
        savedEvaluation.setEtat("ELA");
        savedEvaluation.setNoEvaluation((short) (Math.random() * 100));
        savedEvaluation.setPeriode(evaluationDTO.getPeriode());
        savedEvaluation.setNoEnseignant(enseignant);
        Promotion promotion = promotionRepository.findByPromotionId(evaluationDTO.getCodeFormation(), evaluationDTO.getAnneePro());
        if (promotion == null) {
            throw new NoSuchElementException("La promotion n'existe pas");
        }
        savedEvaluation.setPromotion(promotion);
        UniteEnseignement uniteEnseignement = uniteEnseignementRepository.findById(evaluationDTO.getCodeUE(), evaluationDTO.getCodeFormation());
        if (uniteEnseignement == null) {
            throw new NoSuchElementException("L'unité d'enseignement n'existe pas");
        }
        savedEvaluation.setUniteEnseignement(uniteEnseignement);
        ElementConstitutif elementConstitutif = null;
        if (!evaluationDTO.getCodeEC().isEmpty()) {
            elementConstitutif = elementConstitutifRepository.findById(evaluationDTO.getCodeEC(), evaluationDTO.getCodeUE(), evaluationDTO.getCodeFormation());
            if (elementConstitutif == null) {
                throw new IllegalArgumentException("L'élément constitutif n'existe pas");
            }
            savedEvaluation.setElementConstitutif(elementConstitutif);
            er.insertEvaluation(savedEvaluation.getDesignation(), savedEvaluation.getDebutReponse(), savedEvaluation.getFinReponse(), savedEvaluation.getEtat(), savedEvaluation.getPromotion().getId().getCodeFormation(), savedEvaluation.getPromotion().getId().getAnneeUniversitaire(), savedEvaluation.getNoEvaluation(), savedEvaluation.getPeriode(), savedEvaluation.getElementConstitutif().getId().getCodeEc(), savedEvaluation.getElementConstitutif().getId().getCodeUe(), savedEvaluation.getNoEnseignant().getId().longValue());
        }
        er.insertEvaluation(savedEvaluation.getDesignation(), savedEvaluation.getDebutReponse(), savedEvaluation.getFinReponse(), savedEvaluation.getEtat(), savedEvaluation.getPromotion().getId().getCodeFormation(), savedEvaluation.getPromotion().getId().getAnneeUniversitaire(), savedEvaluation.getNoEvaluation(), savedEvaluation.getPeriode(), null, savedEvaluation.getUniteEnseignement().getId().getCodeUe(), savedEvaluation.getNoEnseignant().getId().longValue());
        savedEvaluation = er.findByDesignation(savedEvaluation.getDesignation());
        logger.info("Saved evaluation: " + savedEvaluation);
        rubriqueEvaluationService.saveRubriquesEvaluation(evaluationDTO.getRubriqueQuestion(), savedEvaluation);
    }


    public List<ReponseEvaluation> getReponsesForEvaluationAndEtudiant(Long idEvaluation, Long noEtudiant) throws ResourceNotFoundException {
        Evaluation evaluation = er.findById(idEvaluation).orElseThrow(() ->
                new ResourceNotFoundException("L'evaluation n'existe pas avec cet id : " + idEvaluation));

        Etudiant etudiant = etudiantRepository.findById(String.valueOf(noEtudiant)).orElseThrow(() ->
                new ResourceNotFoundException("L'etudiant n'existe pas avec cet id : " + noEtudiant));

        List<ReponseEvaluation> reponses = reponseEvaluationRepository.findAllByIdEvaluationAndNoEtudiant(etudiant, evaluation);

        return reponses;
    }
    @Transactional
    @Override
    public void deleteEvaluation(Long id) throws ResourceNotFoundException {
        Optional<Evaluation> evaluationExistant = er.findById(id);
        if (evaluationExistant.isPresent()) {
            Evaluation evaluation = evaluationExistant.get();
            droitRepository.deleteAll(evaluation.getDroits());
            for (ReponseEvaluation reponseEvaluation : evaluation.getReponseEvaluations()) {
                reponseQuestionRepository.deleteAll(reponseEvaluation.getReponseQuestions());
            }
            reponseEvaluationRepository.deleteAll(evaluation.getReponseEvaluations());
            for (RubriqueEvaluation rubriqueEvaluation : evaluation.getRubriqueEvaluations()) {
                for (QuestionEvaluation questionEvaluation : rubriqueEvaluation.getQuestionEvaluations()) {
                    reponseQuestionRepository.deleteAll(questionEvaluation.getReponseQuestions());
                }
            }
            for (RubriqueEvaluation rubriqueEvaluation : evaluation.getRubriqueEvaluations()) {
                questionEvaluationRepository.deleteAll(rubriqueEvaluation.getQuestionEvaluations());
            }
            rubriqueEvaluationRepository.deleteAll(evaluation.getRubriqueEvaluations());
            er.delete(evaluation);
        } else {
            throw new ResourceNotFoundException("L'evaluation n'existe pas avec cet id : " + id);
        }
    }



}
