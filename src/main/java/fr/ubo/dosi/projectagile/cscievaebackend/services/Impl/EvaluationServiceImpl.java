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
    private final EtudiantRepository etudiantRepository;
    private final ReponseEvaluationRepository responseEvaluationRepository;
    private final EvaluationMapper evaluationMapper;
    private final ReponseEvaluationMapper reponseEvaluationMapper;
    private final RubriqueEvaluationRepository rubriqueEvaluationRepository;
    private final DroitRepository droitRepository;
    private final ReponseQuestionRepository reponseQuestionRepository;

    private final QuestionEvaluationRepository questionEvaluationRepository;
    private final RubriqueEvaluationService rubriqueEvaluationService;
    private final ReponseEvaluationRepository reponseEvaluationRepository;
    Logger logger = Logger.getLogger(EvaluationServiceImpl.class.getName());

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository er, EvaluationRepository evaluationRepository, PromotionRepository promotionRepository, UniteEnseignementRepository uniteEnseignementRepository, EvaluationMapper evaluationMapper, ElementConstitutifRepository elementConstitutifRepository, RubriqueEvaluationService rubriqueQuestionService, EtudiantRepository etudiantRepository, ReponseEvaluationRepository responseEvaluationRepository, ReponseEvaluationMapper reponseEvaluationMapper, RubriqueEvaluationRepository rubriqueEvaluationRepository, DroitRepository droitRepository, ReponseQuestionRepository reponseQuestionRepository, QuestionEvaluationRepository questionEvaluationRepository, ReponseEvaluationRepository reponseEvaluationRepository) {
        this.evaluationRepository = evaluationRepository;
        this.promotionRepository = promotionRepository;
        this.uniteEnseignementRepository = uniteEnseignementRepository;
        this.elementConstitutifRepository = elementConstitutifRepository;
        this.evaluationMapper = evaluationMapper;
        this.rubriqueEvaluationRepository = rubriqueEvaluationRepository;
        this.droitRepository = droitRepository;
        this.reponseQuestionRepository = reponseQuestionRepository;
        this.questionEvaluationRepository = questionEvaluationRepository;
        this.rubriqueEvaluationService = rubriqueQuestionService;
        this.etudiantRepository = etudiantRepository;
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
        savedEvaluation.setDesignation(evaluationDTO.getDesignation());
        savedEvaluation.setDebutReponse(evaluationDTO.getDebutReponse());
        savedEvaluation.setFinReponse(evaluationDTO.getFinReponse());
        savedEvaluation.setPeriode(evaluationDTO.getPeriode());
        rubriqueEvaluationService.saveRubriquesEvaluation(evaluationDTO.getRubriqueQuestion(), savedEvaluation);
    }

    @Override
    public String saveReponseEtudiant(ReponseEvaluationDTO reponseEvaluationDTO) {
        if (responseEvaluationRepository.existsByNoEtudiantAndIdEvaluation(reponseEvaluationDTO.getNoEtudiant().getNoEtudiant(), reponseEvaluationDTO.getIdEvaluation().getId().longValue())) {
            throw new IllegalArgumentException("L'étudiant a déjà répondu à cette évaluation");
        }
        Evaluation evaluation = evaluationRepository.findById(reponseEvaluationDTO.getIdEvaluation().getId().longValue()).orElseThrow(() -> new NoSuchElementException("L'évaluation n'existe pas"));
        Etudiant etudiant = etudiantRepository.findById(reponseEvaluationDTO.getNoEtudiant().getNoEtudiant()).orElseThrow(() -> new NoSuchElementException("L'étudiant n'existe pas"));
        ReponseEvaluation responseEvaluation = reponseEvaluationMapper.toEntity(reponseEvaluationDTO);
        logger.info("ReponseEvaluationDTO: " + reponseEvaluationDTO);
        responseEvaluation.setNoEtudiant(etudiant);
        responseEvaluation.setIdEvaluation(evaluation);
        responseEvaluationRepository.save(responseEvaluation);
        return "Reponse enregistrée avec succès";
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
