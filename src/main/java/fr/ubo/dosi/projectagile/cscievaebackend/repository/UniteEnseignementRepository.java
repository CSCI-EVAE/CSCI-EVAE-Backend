package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, String> {
    public List<UniteEnseignement> findAllByNoEnseignant(Enseignant enseignant);

}