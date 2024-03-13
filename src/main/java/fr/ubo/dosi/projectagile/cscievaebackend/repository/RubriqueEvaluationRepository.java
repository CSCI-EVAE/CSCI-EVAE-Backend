package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RubriqueEvaluationRepository extends JpaRepository<RubriqueEvaluation, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RubriqueEvaluation r WHERE r.idRubrique = ?1 AND r.idEvaluation = ?2")
    boolean existsByIdRubriqueAndIdEvaluation(Long idRubrique, Evaluation savedEvaluation);

    @Query("SELECT r FROM RubriqueEvaluation r WHERE r.idRubrique = ?1 AND r.idEvaluation = ?2")
    RubriqueEvaluation findByIdRubriqueAndIdEvaluation(Long idRubrique, Evaluation savedEvaluation);
}