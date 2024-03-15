package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, String> {
    @Override
    Optional<Etudiant> findById(String s);

    Etudiant findByNoEtudiant(String noEtudiant);

}