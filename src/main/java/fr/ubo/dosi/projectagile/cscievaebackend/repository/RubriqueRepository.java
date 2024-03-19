package fr.ubo.dosi.projectagile.cscievaebackend.repository;


import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {

    List<Rubrique> findAllByType(String type);

    boolean existsByDesignationIgnoreCase(String designation);

    boolean existsByType(String type);

    @Modifying
    @Query("SELECT r FROM Rubrique r ORDER BY r.designation")
    List<Rubrique> findAllOrderByDesignation();
}