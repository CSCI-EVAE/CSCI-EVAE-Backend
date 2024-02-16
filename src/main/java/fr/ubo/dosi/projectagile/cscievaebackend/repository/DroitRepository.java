package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Droit;
import fr.ubo.dosi.projectagile.cscievaebackend.model.DroitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroitRepository extends JpaRepository<Droit, DroitId> {
}