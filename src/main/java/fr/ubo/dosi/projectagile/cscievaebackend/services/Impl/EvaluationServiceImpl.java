package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EvaluationServiceImpl implements EvaluationService {
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



    @Override
    public Evaluation getEvaluationById(Long id) {
        return er.findById(id).get();
    }

}
