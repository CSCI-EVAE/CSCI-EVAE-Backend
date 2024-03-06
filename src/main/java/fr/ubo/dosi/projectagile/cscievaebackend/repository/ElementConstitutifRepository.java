package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutifId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElementConstitutifRepository extends JpaRepository<ElementConstitutif, ElementConstitutifId> {
    //ElementConstitutif findByCodeUEAndCodeEC(String codeUE, String codeEC);
    @Query("SELECT ec FROM ElementConstitutif ec WHERE ec.id.codeEc = :codeEc AND ec.id.codeUe = :codeUe")
    ElementConstitutif findByCodeEcAndCodeUe(String codeEc, String codeUe);

}