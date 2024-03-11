package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, String> {
    Etudiant findByNoEtudiant(String noEtudiant);

    List<Etudiant> findByPromotion(Promotion promotion);

}