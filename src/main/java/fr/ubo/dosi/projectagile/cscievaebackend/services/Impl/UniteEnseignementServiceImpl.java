package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;


import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.FormationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.UniteEnseignementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
@Service
public class UniteEnseignementServiceImpl implements UniteEnseignementService {

    @Autowired
    private EvaluationRepository er;
    @Autowired
    private FormationRepository formationRepository;

    public List<Evaluation> getUeListe (Enseignant enseignant){
        return er.findAllByNoEnseignant(enseignant);
    }

    @Override
    public List<UniteEnseignement> getAllUE() {
        return er.findAllOrderByCodeUe();
    }

    @Override
    public List<UniteEnseignement> getAllUEByPromotions(String codeFormation) {
        Formation formation = formationRepository.findById( codeFormation).get();
        // for all add setTotalHeures to the sum of all the hours
        return formation.getUniteEnseignements().stream().map(ue -> {
            ue.setTotalHeures();
            return ue;
        }).toList();
    }
}
