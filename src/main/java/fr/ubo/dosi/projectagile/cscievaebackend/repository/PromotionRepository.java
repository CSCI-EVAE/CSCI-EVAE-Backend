package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, String> {
    @Query(value = "SELECT * FROM Promotion p WHERE p.CODE_FORMATION = :codef", nativeQuery = true)
    List<Promotion> findByCodeFormation(@Param("codef") String codef);
    @Query(value = "SELECT * FROM Promotion p WHERE p.CODE_FORMATION = :codeFormationId LIMIT 1", nativeQuery = true)
    Promotion findByCodeFormationId(@Param("codeFormationId") String codeFormationId);
    @Query(value = "SELECT * FROM Promotion p WHERE p.CODE_FORMATION = :codeFormation AND p.ANNEE_UNIVERSITAIRE = :anneeUniversitaire", nativeQuery = true)
    Promotion findByCodeFormationAndAnneeUniversitaire(@Param("codeFormation") String codeFormation, @Param("anneeUniversitaire") String anneeUniversitaire);
}