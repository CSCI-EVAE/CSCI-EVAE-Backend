package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QualificatifRepository extends JpaRepository<Qualificatif, Long> {
    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Qualificatif q WHERE q.minimal = :minimal AND q.maximal = :maximal")
    boolean existsByMinimalAndMaximal(String minimal, String maximal);
    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM Qualificatif q WHERE q.id = :id")
    boolean existsByQualificatifId(Integer id);
}