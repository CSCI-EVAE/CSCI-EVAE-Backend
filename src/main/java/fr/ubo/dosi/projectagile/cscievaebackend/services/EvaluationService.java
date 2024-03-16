package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.ReponseEvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
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

    void saveEvaluation(EvaluationSaveDTO evaluationDTO, Enseignant currentUser);

    void updateEvaluationEns(EvaluationSaveDTO evaluationDTO, Enseignant ens);

    String deleteReponse(Integer id);

    String saveReponseEtudiant(ReponseEvaluationDTO reponseEvaluationDTO, Etudiant etu);

    EvaluationDTO getStatistics(Long id);

    EvaluationDTO getReponsesEtudiant(Integer id, Etudiant etu);

    void deleteEvaluation(Long id) throws ResourceNotFoundException;
}
