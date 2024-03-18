package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, String> {
    @Query("SELECT ue FROM UniteEnseignement ue WHERE ue.id.codeUe = :codeUe AND ue.id.codeFormation = :codeFormation ")
    UniteEnseignement findById(String codeUe, String codeFormation);
}