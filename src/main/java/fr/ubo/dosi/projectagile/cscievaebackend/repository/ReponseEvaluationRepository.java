package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReponseEvaluationRepository extends JpaRepository<ReponseEvaluation, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReponseEvaluation r WHERE r.noEtudiant = ?1 AND r.idEvaluation = ?2")
    boolean existsByNoEtudiantAndIdEvaluation(String noEtudiant, long l);
}