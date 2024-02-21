package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;

import java.util.List;

public interface EvaluationService {
    public List<Evaluation> getAll();


    Evaluation getEvaluationById(Long id);

    EvaluationDTO updateEvaluation(Long id) throws ResourceNotFoundException;
}
