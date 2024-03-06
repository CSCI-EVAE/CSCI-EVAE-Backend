package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FormationRepository extends JpaRepository<Formation, String> {
    boolean findByCodeFormation(String info);

    //Formation findByNomFormation(String informatique);
    @Query(value = "SELECT * FROM (SELECT * FROM Formation f WHERE f.NOM_FORMATION = :nomFormation) WHERE ROWNUM <= 1", nativeQuery = true)
    Formation findByNomFormation(@Param("nomFormation") String nomFormation);


}