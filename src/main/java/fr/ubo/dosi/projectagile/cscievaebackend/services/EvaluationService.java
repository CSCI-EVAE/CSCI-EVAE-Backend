package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import java.util.List;

public interface EvaluationService {

    Evaluation getEvaluationById(Long id);

    List<Evaluation> getEvaluationsForEnseignantLastYear(Long enseignantId);

    EvaluationDTO updateEvaluation(Long id) throws ResourceNotFoundException;
}
