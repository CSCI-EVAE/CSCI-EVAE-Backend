package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationRepository er;

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


    public Evaluation createEvaluation(Evaluation evaluation) {
        return er.save(evaluation);
    }
}
