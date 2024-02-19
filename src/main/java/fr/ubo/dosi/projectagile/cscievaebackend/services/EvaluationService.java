package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;

import java.util.List;
import java.util.Set;

public interface EvaluationService {
    public List<Evaluation> getAll();


    Evaluation getEvaluationById(Long id);
}
