package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.Model.ElementConstitutif;
import fr.ubo.dosi.projectagile.cscievaebackend.Model.ElementConstitutifId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementConstitutifRepository extends JpaRepository<ElementConstitutif, ElementConstitutifId> {
}