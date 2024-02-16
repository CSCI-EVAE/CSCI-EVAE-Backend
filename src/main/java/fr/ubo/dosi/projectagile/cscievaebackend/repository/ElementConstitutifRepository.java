package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutifId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementConstitutifRepository extends JpaRepository<ElementConstitutif, ElementConstitutifId> {
}