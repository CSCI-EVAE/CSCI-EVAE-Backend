package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.*;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationRepository er;
   @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ElementConstitutifRepository elementConstitutifRepository;
    @Autowired
    FormationRepository formationRepository;
    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Autowired
    RubriqueQuestionService rubriqueQuestionService;
    @Autowired
    AuthentificationService authentificationService;
    @Autowired
    EnseignantRepository enseignantRepository;
    Logger logger = Logger.getLogger(EvaluationServiceImpl.class.getName());

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

    @Override
    public Set<Evaluation> getEvaluationsByUser(Etudiant etudiant) {
        Promotion promotion = etudiant.getPromotion();
        logger.info("Promotion found: " + promotion);
        return promotion.getEvaluations();
    }

    public Evaluation createEvaluation(Evaluation evaluation, Enseignant enseignant) {
        if (er.existsByNoEvaluation(evaluation.getNoEvaluation())) {
            throw new IllegalArgumentException("L'évaluation existe déjà");
        }
        evaluation.setNoEnseignant(enseignant);
        return er.save(evaluation);
    }
    //////


    public Evaluation createEvaluation(EvaluationSaveDTO evaluationDto) {
        // 1. Récupérer d'ElementConstitutif  codeUE et codeEC
        ElementConstitutif elementConstitutif = elementConstitutifRepository.findByCodeEcAndCodeUe(evaluationDto.getCodeEC(),evaluationDto.getCodeUE());
        // recuperer le code formation
        //Formation formation =formationRepository.findByNomFormation(evaluationDto.getNomFormation());
        // 2. Récupérer l'instance de Promotion en fonction du nom de la formation (nomFormation)
        Promotion promotion = promotionRepository.findByCodeFormationAndAnneeUniversitaire("M2DOSI",evaluationDto.getAnneePro());
        //
        System.out.println(promotion.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        final Authentification authentification = authentificationService.getAuhtentification(userDetails.getUsername());
        System.out.println(authentification.getNoEnseignant().getId());
        Optional<Enseignant> enseignantOptional=enseignantRepository.findByEmailUbo(authentification.getNoEnseignant().getEmailUbo());
        Enseignant enseignant = enseignantOptional.orElse(null);
        System.out.println(enseignant);

        // 3. Créer une nouvelle instance d'Evaluation
        Evaluation evaluation = new Evaluation();
        //evaluation.setId(3);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh7");
        evaluation.setElementConstitutif(elementConstitutif);
        evaluation.setNoEnseignant(enseignant);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh8");
        evaluation.setNoEvaluation(generateRandomNoEvaluation());
        evaluation.setDesignation(evaluationDto.getDesignation());
        evaluation.setDebutReponse(evaluationDto.getDebutReponse());
        evaluation.setFinReponse(evaluationDto.getFinReponse());
        evaluation.setEtat("ELA");
        evaluation.setPeriode("....");
        evaluation.setPromotion(promotion);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh9");
        //je reviens pour Set<RubriqueEvaluation> rubriqueEvaluations
        // Enregistrer cette nouvelle instance d'Evaluation dans la base de données
        Evaluation savedEvaluation = er.save(evaluation);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh10");
        // 4. Pour chaque RubriqueQuestionSaveDTO dans la liste RubriqueQuestion de EvaluationSaveDTO
        for (RubriqueQuestionSaveDTO rubriqueQuestionDto : evaluationDto.getRubriqueQuestion()) {
            // 5. Récupérer ou créer une nouvelle instance de Rubrique en utilisant la désignation (designation)
            Rubrique rubrique = rubriqueRepository.findByDesignation(rubriqueQuestionDto.getDesignation());
            if (rubrique == null) {
                rubrique = new Rubrique();
                rubrique.setDesignation(rubriqueQuestionDto.getDesignation());
                rubrique = rubriqueRepository.save(rubrique);
            }
            List<Long> questionIds = new ArrayList<>();
            for (QuestionDTO questionDTO : rubriqueQuestionDto.getQuestions()) {
                // Ajouter l'ID de chaque question à la liste
                questionIds.add(questionDTO.getId().longValue());
            }
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh11");
            IncomingRubriqueQuestionDTO incomingRubriqueQuestionDTO=new IncomingRubriqueQuestionDTO();
            incomingRubriqueQuestionDTO.setIdRubrique(rubrique.getId().longValue());
            incomingRubriqueQuestionDTO.setOrdre(rubriqueQuestionDto.getOrdre());
            incomingRubriqueQuestionDTO.setQuestionIds(questionIds);
            String resultat=rubriqueQuestionService.processAndStore((List<IncomingRubriqueQuestionDTO>) incomingRubriqueQuestionDTO);

            if (resultat.equals("All data processed successfully.")) {
                return savedEvaluation;
            }else {
                return null;
            }
//            // 6. Enregistrer une nouvelle instance de RubriqueEvaluation
//            RubriqueEvaluation rubriqueEvaluation = new RubriqueEvaluation();
//            rubriqueEvaluation.setIdEvaluation(savedEvaluation);
//            rubriqueEvaluation.setIdRubrique(rubrique);
             // rubriqueEvaluationRepository.save(rubriqueEvaluation);
        }
        return savedEvaluation;

    }
    private Short generateRandomNoEvaluation() {
        Random rand = new Random();
        return (short) (rand.nextInt(1000) + 1);
    }

}


/*
* public List<PromotionDTOResponse> trouverPromotionsByEnseignant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        final Optional<Authentification> authentification = authentificationService.findByLoginConnection(userDetails.getUsername());

        return authentification.map(a -> {
            Enseignant ens = a.getNoEnseignant();

            // Vérifier si l'objet Enseignant n'est pas null
            if (ens != null) {
                // Continuer avec la logique existante
                List<Promotion> promotions = promotionRepository.findByNoEnseignant(ens);
                System.out.println("Promotions: " + promotions);

                // Obtenir le nombre d'étudiants par promotion
                Map<String, Long> nombreEtudiantsParPromotion = this.getNombreEtudiantsParPromotion();

                return promotions.stream()
                        .map(p -> {
                            PromotionDTOResponse dto = new PromotionDTOResponse();
                            dto.setCodeFormation(p.getCodeFormation().getCodeFormation());
                            dto.setAnneeUniversitaire(p.getId().getAnneeUniversitaire());
                            dto.setProcessusStage(p.getProcessusStage());
                            dto.setNbMaxEtudiant(p.getNbMaxEtudiant());
                            // Ajouter le nombre d'étudiants dans le DTO de la promotion
                            dto.setNombreEtudiants(nombreEtudiantsParPromotion.getOrDefault(p.getId().getCodeFormation() + "-" + p.getId().getAnneeUniversitaire(), 0L));
                            return dto;
                        })
                        .collect(Collectors.toList());
            } else {
                // Gérer le cas où Enseignant est null, par exemple, retourner une liste vide
                return Collections.<PromotionDTOResponse>emptyList();
            }
        }).orElseGet(() -> Collections.<PromotionDTOResponse>emptyList());
    }*/
