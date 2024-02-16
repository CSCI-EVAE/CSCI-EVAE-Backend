package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.Model.Droit;
import fr.ubo.dosi.projectagile.cscievaebackend.Model.DroitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroitRepository extends JpaRepository<Droit, DroitId> {
}