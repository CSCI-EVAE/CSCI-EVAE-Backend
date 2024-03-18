package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.*;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.ReponseEvaluationMapper;
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
    private final EvaluationRepository evaluationRepository;
    private final PromotionRepository promotionRepository;
    private final UniteEnseignementRepository uniteEnseignementRepository;
    private final ElementConstitutifRepository elementConstitutifRepository;
    private final RubriqueEvaluationService rubriqueEvaluationService;
    private final QuestionEvaluationRepository questionEvaluationRepository;
    private final ReponseEvaluationRepository responseEvaluationRepository;
    private final EvaluationMapper evaluationMapper;
    private final ReponseEvaluationMapper reponseEvaluationMapper;
    private final RubriqueEvaluationRepository rubriqueEvaluationRepository = null;
    private final DroitRepository droitRepository;
    private final ReponseQuestionRepository reponseQuestionRepository;

    private final ReponseEvaluationRepository reponseEvaluationRepository;
    Logger logger = Logger.getLogger(EvaluationServiceImpl.class.getName());

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository er, PromotionRepository promotionRepository, UniteEnseignementRepository uniteEnseignementRepository, EvaluationMapper evaluationMapper, ElementConstitutifRepository elementConstitutifRepository, RubriqueEvaluationService rubriqueQuestionService, QuestionEvaluationRepository questionEvaluationRepository, ReponseEvaluationRepository responseEvaluationRepository, ReponseEvaluationMapper reponseEvaluationMapper,
                                 ReponseQuestionRepository reponseQuestionRepository, DroitRepository droitRepository, ReponseEvaluationRepository reponseEvaluationRepository) {
        this.evaluationRepository = er;
        this.promotionRepository = promotionRepository;
        this.uniteEnseignementRepository = uniteEnseignementRepository;
        this.elementConstitutifRepository = elementConstitutifRepository;
        this.evaluationMapper = evaluationMapper;
        this.droitRepository = droitRepository;
        this.reponseQuestionRepository = reponseQuestionRepository;
        this.questionEvaluationRepository = questionEvaluationRepository;
        this.rubriqueEvaluationService = rubriqueQuestionService;
        this.responseEvaluationRepository = responseEvaluationRepository;
        this.reponseEvaluationMapper = reponseEvaluationMapper;
        this.reponseEvaluationRepository = reponseEvaluationRepository;
    }


    public EvaluationDTO getEvaluationById(Long id) throws ChangeSetPersister.NotFoundException {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        logger.info("Evaluation found: " + evaluation);
        return evaluationMapper.evaluationToEvaluationDTO(evaluation);
    }

    @Override
    public List<Evaluation> getEvaluationsForEnseignantLastYear(Long enseignantId) {
        String lastYear = String.valueOf(LocalDate.now().getYear() - 1);
        return evaluationRepository.findAllByEnseignantAndLastYear(enseignantId, lastYear);
    }

    @Override
    public EvaluationDTO updateEvaluation(Long id) throws ResourceNotFoundException {
        Optional<Evaluation> evaluationExistant = evaluationRepository.findById(id);

        if (evaluationExistant.isPresent()) {
            Evaluation evaluation = evaluationExistant.get();
            evaluation.setEtat("DIS");
            System.out.println("Evaluation to be saved: " + evaluation);
            evaluationRepository.save(evaluation);
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
        if (evaluationRepository.existsByDesignation(evaluationDTO.getDesignation())) {
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
            evaluationRepository.insertEvaluation(savedEvaluation.getDesignation(), savedEvaluation.getDebutReponse(), savedEvaluation.getFinReponse(), savedEvaluation.getEtat(), savedEvaluation.getPromotion().getId().getCodeFormation(), savedEvaluation.getPromotion().getId().getAnneeUniversitaire(), savedEvaluation.getNoEvaluation(), savedEvaluation.getPeriode(), savedEvaluation.getElementConstitutif().getId().getCodeEc(), savedEvaluation.getElementConstitutif().getId().getCodeUe(), savedEvaluation.getNoEnseignant().getId().longValue());
        }
        evaluationRepository.insertEvaluation(savedEvaluation.getDesignation(), savedEvaluation.getDebutReponse(), savedEvaluation.getFinReponse(), savedEvaluation.getEtat(), savedEvaluation.getPromotion().getId().getCodeFormation(), savedEvaluation.getPromotion().getId().getAnneeUniversitaire(), savedEvaluation.getNoEvaluation(), savedEvaluation.getPeriode(), null, savedEvaluation.getUniteEnseignement().getId().getCodeUe(), savedEvaluation.getNoEnseignant().getId().longValue());
        savedEvaluation = evaluationRepository.findByDesignation(savedEvaluation.getDesignation());
        logger.info("Saved evaluation: " + savedEvaluation);
        rubriqueEvaluationService.saveRubriquesEvaluation(evaluationDTO.getRubriqueQuestion(), savedEvaluation);
    }

    @Transactional
    @Override
    public void updateEvaluationEns(EvaluationSaveDTO evaluationDTO, Enseignant ens) {
        Evaluation savedEvaluation = evaluationRepository.findById(evaluationDTO.getId()).orElseThrow(() -> new NoSuchElementException("L'évaluation n'existe pas"));

        if (evaluationRepository.existsByDesignation(evaluationDTO.getDesignation())) {
            throw new IllegalArgumentException("L'évaluation existe déjà avec cette désignation");
        }
        savedEvaluation.setDesignation(evaluationDTO.getDesignation());
        savedEvaluation.setDebutReponse(evaluationDTO.getDebutReponse());
        savedEvaluation.setFinReponse(evaluationDTO.getFinReponse());
        savedEvaluation.setPeriode(evaluationDTO.getPeriode());
        rubriqueEvaluationService.saveRubriquesEvaluation(evaluationDTO.getRubriqueQuestion(), savedEvaluation);
    }

    @Transactional
    @Override
    public String saveReponseEtudiant(ReponseEvaluationDTO reponseEvaluationDTO, Etudiant etudiant) {
        if (reponseEvaluationDTO.getId() != null) {
            deleteReponse(reponseEvaluationDTO.getId());
        }
        if (responseEvaluationRepository.existsByNoEtudiantAndIdEvaluation(etudiant.getNoEtudiant(), reponseEvaluationDTO.getIdEvaluationId())) {
            logger.info("ID Evaluation: " + reponseEvaluationDTO.getIdEvaluationId()+ " No Etudiant: " + etudiant.getNoEtudiant());
            throw new IllegalArgumentException("L'étudiant a déjà répondu à cette évaluation");
        }
        Evaluation evaluation = evaluationRepository.findById(reponseEvaluationDTO.getIdEvaluationId().longValue()).orElseThrow(() -> new NoSuchElementException("L'évaluation n'existe pas"));
        ReponseEvaluation responseEvaluation = new ReponseEvaluation();
        responseEvaluation.setCommentaire(reponseEvaluationDTO.getCommentaire());
        responseEvaluation.setNom(reponseEvaluationDTO.getNom());
        responseEvaluation.setPrenom(reponseEvaluationDTO.getPrenom());
        responseEvaluation.setNoEtudiant(etudiant);
        responseEvaluation.setIdEvaluation(evaluation);
        ReponseEvaluation savedResponce = responseEvaluationRepository.save(responseEvaluation);
        savedResponce.getReponseQuestions().clear();
        for (ReponseEvaluationDTO.ReponseQuestionDto reponseQ : reponseEvaluationDTO.getReponseQuestions()) {
            ReponseQuestion reponseQuestion = new ReponseQuestion();
            reponseQuestion.setIdReponseEvaluation(savedResponce);
            QuestionEvaluation questionEvaluation = questionEvaluationRepository.findById(reponseQ.getIdQuestionEvaluation().getId().longValue()).orElseThrow(() -> new NoSuchElementException("La question n'existe pas"));
            reponseQuestion.setIdQuestionEvaluation(questionEvaluation);
            reponseQuestion.setPositionnement(reponseQ.getPositionnement());
            reponseQuestion.setId(new ReponseQuestionId(savedResponce.getId(), reponseQ.getIdQuestionEvaluation().getId()));
            ReponseQuestion savedReponseQuestion = reponseQuestionRepository.save(reponseQuestion);
            savedResponce.getReponseQuestions().add(savedReponseQuestion);
        }
        responseEvaluationRepository.save(savedResponce);
        return "Reponse enregistrée avec succès";
    }

    @Override
    public EvaluationDTO getStatistics(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("L'évaluation n'existe pas"));
        EvaluationDTO evaluationDTO = evaluationMapper.evaluationToEvaluationDTO(evaluation);
        evaluationDTO.getRubriqueEvaluations().forEach(rubriqueEvaluation -> {
            rubriqueEvaluation.getQuestionEvaluations().forEach(questionEvaluation -> {
                List<ReponseQuestion> reponseQuestions = reponseQuestionRepository.findByIdQuestionEvaluationId(questionEvaluation.getId());
                if (!reponseQuestions.isEmpty()) {
                    double average = reponseQuestions.stream().mapToDouble(ReponseQuestion::getPositionnement).average().orElse(0.0);
                    questionEvaluation.setMoyen(average);
                }
            });
        });
        return evaluationDTO;
    }

    @Override
    public EvaluationDTO getReponsesEtudiant(Integer id, Etudiant etu) {
        List<ReponseEvaluation> reponseEvaluations = responseEvaluationRepository.findByIdEvaluationIdAndNoEtudiant(etu.getNoEtudiant(), id.longValue());
        if (reponseEvaluations.isEmpty()) {
            throw new NoSuchElementException("La réponse n'existe pas");
        }
        ReponseEvaluation reponseEvaluation = reponseEvaluations.get(0);
        EvaluationDTO evaluation = evaluationMapper.evaluationToEvaluationDTO(evaluationRepository.findById(id.longValue()).orElseThrow(() -> new NoSuchElementException("L'évaluation n'existe pas")));
        evaluation.setCommentaire(reponseEvaluation.getCommentaire());
        evaluation.setNomEtudiant(reponseEvaluation.getNom());
        evaluation.setPrenomEtudiant(reponseEvaluation.getPrenom());
        evaluation.getRubriqueEvaluations().forEach(rubriqueEvaluation -> {
            rubriqueEvaluation.getQuestionEvaluations().forEach(questionEvaluation -> {
                reponseQuestionRepository.findByIdReponseEvaluationIdAndIdQuestionEvaluationId(reponseEvaluation.getId(), questionEvaluation.getId()).ifPresent(reponseQuestion -> questionEvaluation.setPositionnement(reponseQuestion.getPositionnement().intValue()));
            });
        });
        return evaluation;
    }

    @Override
    public String deleteReponse(Integer id) {
        ReponseEvaluation reponseEvaluation = responseEvaluationRepository.findById(id.longValue()).orElseThrow(() -> new NoSuchElementException("La réponse n'existe pas"));
        reponseEvaluation.getReponseQuestions().forEach(reponseQuestion -> reponseQuestionRepository.deleteById(reponseQuestion.getId()));
        responseEvaluationRepository.deleteById(id.longValue());
        return "Reponse supprimée avec succès";
    }

    @Transactional
    @Override
    public void deleteEvaluation(Long id) throws ResourceNotFoundException {
        Optional<Evaluation> evaluationExistant = evaluationRepository.findById(id);
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
            evaluationRepository.delete(evaluation);
        } else {
            throw new ResourceNotFoundException("L'evaluation n'existe pas avec cet id : " + id);
        }
    }
}
