package fr.ubo.dosi.projectagile.cscievaebackend.repository;


import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RubriqueEvaluationRepository extends JpaRepository<RubriqueEvaluation, Long> {

    @Query("select r from RubriqueEvaluation r where r.idRubrique.id = ?1 and r.idEvaluation.id = ?2")
    Optional<RubriqueEvaluation> findByIdRubriqueAndIdEvaluation(Long idRubrique, Long idEvaluation);
}