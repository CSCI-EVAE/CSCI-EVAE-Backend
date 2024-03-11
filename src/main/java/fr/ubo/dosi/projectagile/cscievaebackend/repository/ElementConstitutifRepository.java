package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutifId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElementConstitutifRepository extends JpaRepository<ElementConstitutif, ElementConstitutifId> {
    @Query("SELECT ec FROM ElementConstitutif ec WHERE ec.id.codeEc = :codeEc AND ec.id.codeUe = :codeUe AND ec.id.codeFormation = :codeFormation ")
    ElementConstitutif findById(String codeEc, String codeUe, String codeFormation);
}