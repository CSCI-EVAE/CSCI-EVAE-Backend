package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface EvaluationService {

    EvaluationDTO getEvaluationById(Long id) throws ChangeSetPersister.NotFoundException;

    List<Evaluation> getEvaluationsForEnseignantLastYear(Long enseignantId);

    EvaluationDTO updateEvaluation(Long id) throws ResourceNotFoundException;

    Set<Evaluation> getEvaluationsByUser(Etudiant etudiant);
    Evaluation createEvaluation(EvaluationSaveDTO evaluationDto);
}
