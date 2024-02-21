package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

        @Query(value = "SELECT * FROM EVALUATION e WHERE e.CODE_UE = :codeUE", nativeQuery = true)
        List<Evaluation> findByCodeUE(@Param("codeUE") String codeUE);
        List<Evaluation> findAllByNoEnseignant(Enseignant noEnseignant);
        @Query("SELECT e FROM Evaluation e WHERE e.noEnseignant.id = :enseignantId AND e.promotion.id.anneeUniversitaire LIKE :lastYear%")
        List<Evaluation> findAllByEnseignantAndLastYear(@Param("enseignantId") Long enseignantId, @Param("lastYear") String lastYear);

}