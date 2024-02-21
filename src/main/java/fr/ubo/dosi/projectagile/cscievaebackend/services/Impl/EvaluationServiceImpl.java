package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveOrUpdateDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationRepository er;
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ElementConstitutifRepository elementConstitutifRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private RubriqueEvaluationRepository rubriqueEvaluationRepository;

    @Autowired
    private QuestionEvaluationRepository questionEvaluationRepository;

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
        return er.findById(id).get();
    }

//    public ApiResponse<String> saveOrUpdateEvaluation(EvaluationSaveOrUpdateDTO evaluationDTO) {
//        // Vérifiez si l'évaluation existe déjà
//        Optional<Evaluation> existingEvaluation = evaluationRepository.findById(evaluationDTO.getId());
//        if (existingEvaluation.isPresent() && "true".equals(existingEvaluation.get().getEtat())) {
//            return ApiResponse.error("L'évaluation existe déjà", null);
//        }
//
//        // Si l'évaluation n'existe pas ou si son état est false, ajoutez ou mettez à jour
//        Evaluation evaluation = existingEvaluation.orElse(new Evaluation());
//
//        // Mapper les données de DTO à l'objet Evaluation
//        evaluation.setDesignation(evaluationDTO.getDesignation());
//        evaluation.setDebutReponse(evaluationDTO.getDebutReponse());
//        evaluation.setFinReponse(evaluationDTO.getFinReponse());
//        evaluation.setPeriode(evaluationDTO.getPeriode());
//        evaluation.setNoEvaluation(evaluationDTO.getNoEvaluation());
//        evaluation.setEtat("true"); // Mettez l'état à true
//        // Set other fields as needed
//
//        // Fetch related entities from repositories
//        ElementConstitutif elementConstitutif = elementConstitutifRepository.findById(evaluationDTO.getIdElementConstitutif().getId()).orElse(null);
//        Promotion promotion = promotionRepository.findById(evaluationDTO.getIdPromotion().getId()).orElse(null);
//        Enseignant enseignant = enseignantRepository.findById(evaluationDTO.getIdNoEnseignant().getId()).orElse(null);
//
//        if (elementConstitutif == null || promotion == null || enseignant == null) {
//            return ApiResponse.error("Certaines informations liées à l'évaluation sont invalides", null);
//        }
//
//        // Set related entities
//        evaluation.setElementConstitutif(elementConstitutif);
//        evaluation.setPromotion(promotion);
//        evaluation.setNoEnseignant(enseignant);
//
//        // Enregistrer l'évaluation
//        evaluationRepository.save(evaluation);
//
//        return ApiResponse.ok("L'évaluation a été enregistrée avec succès");
//    }


//@Override
public ApiResponse<String> saveOrUpdateEvaluation(EvaluationSaveOrUpdateDTO evaluationDTO) {
    // Créer une nouvelle instance d'évaluation et mapper les données de l'objet DTO
    Evaluation newEvaluation = new Evaluation();
    // Mapper les attributs de l'évaluation depuis le DTO
    newEvaluation.setDesignation(evaluationDTO.getDesignation());
    newEvaluation.setDebutReponse(evaluationDTO.getDebutReponse());
    newEvaluation.setFinReponse(evaluationDTO.getFinReponse());
    newEvaluation.setPeriode(evaluationDTO.getPeriode());
    newEvaluation.setEtat("ELA"); // Mettre à jour l'attribut 'etat' à true
    newEvaluation.setNoEvaluation(evaluationDTO.getNoEvaluation());
    // Enregistrer l'évaluation dans la base de données
    evaluationRepository.save(newEvaluation);

    // Enregistrer les rubriques d'évaluation
    if (evaluationDTO.getRubriqueEvaluations() != null && !evaluationDTO.getRubriqueEvaluations().isEmpty()) {
        for (RubriqueEvaluationDTO rubriqueDTO : evaluationDTO.getRubriqueEvaluations()) {
            RubriqueEvaluation rubriqueEvaluation = new RubriqueEvaluation();
            // Mapper les données de la rubrique depuis le DTO
            // Sauvegarder la rubrique dans la base de données
            rubriqueEvaluationRepository.save(rubriqueEvaluation);
        }
    }

    // Enregistrer les questions d'évaluation
    if (evaluationDTO.getQuestionEvaluations() != null && !evaluationDTO.getQuestionEvaluations().isEmpty()) {
        for (QuestionEvaluationDTO questionDTO : evaluationDTO.getQuestionEvaluations()) {
            QuestionEvaluation questionEvaluation = new QuestionEvaluation();
            questionEvaluationRepository.save(questionEvaluation);
        }
    }
    // Retourner une réponse indiquant que l'évaluation a été enregistrée avec succès
    return ApiResponse.ok("L'évaluation a été enregistrée avec succès");
}

}
