package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthentificationRepository extends JpaRepository<Authentification, Long> {
    Authentification findByLoginConnection(String username);

    Optional<Authentification> findByNoEtudiant_NoEtudiant(String noEtudiant);
}