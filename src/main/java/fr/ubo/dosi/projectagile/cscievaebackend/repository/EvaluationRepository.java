package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "SELECT * FROM EVALUATION e WHERE e.CODE_UE = :codeUE", nativeQuery = true)
    List<Evaluation> findByCodeUE(@Param("codeUE") String codeUE);

    List<Evaluation> findAllByNoEnseignant(Enseignant noEnseignant);

    @Query("SELECT e FROM Evaluation e WHERE e.noEnseignant.id = :enseignantId AND e.promotion.id.anneeUniversitaire LIKE :lastYear%")
    List<Evaluation> findAllByEnseignantAndLastYear(@Param("enseignantId") Long enseignantId, @Param("lastYear") String lastYear);

    boolean existsByNoEvaluation(Short noEvaluation);

    @Query("SELECT e FROM Evaluation e WHERE e.noEnseignant.id = :enseignantId AND e.promotion.id.anneeUniversitaire LIKE :lastYear%")
    List<UniteEnseignement> findAllOrderByCodeUe();

    boolean existsByDesignation(String designation);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO EVALUATION (DESIGNATION, DEBUT_REPONSE, FIN_REPONSE, ETAT, CODE_FORMATION, ANNEE_UNIVERSITAIRE, NO_EVALUATION, PERIODE, CODE_EC, CODE_UE, NO_ENSEIGNANT) VALUES (:designation, :debutReponse, :finReponse, :etat, :codeFormation, :anneeUniversitaire, :noEvaluation, :periode, :codeEc, :codeUe, :noEnseignant)", nativeQuery = true)
    void insertEvaluation(@Param("designation") String designation, @Param("debutReponse") LocalDate debutReponse, @Param("finReponse") LocalDate finReponse, @Param("etat") String etat, @Param("codeFormation") String codeFormation, @Param("anneeUniversitaire") String anneeUniversitaire, @Param("noEvaluation") Short noEvaluation, @Param("periode") String periode, @Param("codeEc") String codeEc, @Param("codeUe") String codeUe, @Param("noEnseignant") Long noEnseignant);

    Evaluation findByDesignation(String designation);
}