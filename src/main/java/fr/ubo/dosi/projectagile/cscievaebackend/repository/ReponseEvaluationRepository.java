package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReponseEvaluationRepository extends JpaRepository<ReponseEvaluation, Long> {
    List<ReponseEvaluation> findAllByIdEvaluationAndNoEtudiant(Etudiant noEtudiant, Evaluation idEvaluation);
}