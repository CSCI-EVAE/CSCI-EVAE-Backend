package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository er;

    public List<Evaluation> getAll() {
        try {
            return er.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
