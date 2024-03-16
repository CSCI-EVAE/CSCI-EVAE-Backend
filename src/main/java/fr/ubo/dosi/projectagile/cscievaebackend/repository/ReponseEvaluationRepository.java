package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReponseEvaluationRepository extends JpaRepository<ReponseEvaluation, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReponseEvaluation r WHERE r.noEtudiant.noEtudiant = ?1 and r.idEvaluation.id = ?2")
    boolean existsByNoEtudiantAndIdEvaluation(String noEtudiant, Integer idEvaluation);
    @Query("select r from ReponseEvaluation r where r.noEtudiant.noEtudiant = ?1 and r.idEvaluation.id = ?2")
    List<ReponseEvaluation> findByIdEvaluationIdAndNoEtudiant(String noEtudiant, long l);
}