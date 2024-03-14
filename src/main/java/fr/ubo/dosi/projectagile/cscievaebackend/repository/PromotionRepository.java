package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, String> {
    @Query(value = "SELECT * FROM Promotion p WHERE p.CODE_FORMATION = :codef", nativeQuery = true)
    List<Promotion> findByCodeFormation(@Param("codef") String codef);

    @Query("select p from Promotion p where p.id.codeFormation = :codef and p.id.anneeUniversitaire = :annee")
    Promotion findByPromotionId(String codef, String annee);

    Promotion findById_CodeFormationAndId_AnneeUniversitaire(String codeFormation, String anneeUniversitaire);

}