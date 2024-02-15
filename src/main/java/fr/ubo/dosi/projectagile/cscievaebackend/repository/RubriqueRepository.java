package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {

    List<Rubrique> findAllByType(String type);
}