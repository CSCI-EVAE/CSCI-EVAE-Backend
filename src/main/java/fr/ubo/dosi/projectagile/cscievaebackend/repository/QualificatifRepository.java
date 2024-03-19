package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QualificatifRepository extends JpaRepository<Qualificatif, Long> {
    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Qualificatif q WHERE lower(q.minimal) = lower(:minimal) AND lower(q.maximal) = lower(:maximal)")
    boolean existsByMinimalAndMaximalIgnoreCase(String minimal, String maximal);
    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Qualificatif q WHERE q.id = :id")
    boolean existsByQualificatifId(Integer id);
    @Modifying
    @Query("select q from Qualificatif q")
    List<Qualificatif> findAll();
}