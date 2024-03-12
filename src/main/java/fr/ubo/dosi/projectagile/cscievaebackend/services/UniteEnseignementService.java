package fr.ubo.dosi.projectagile.cscievaebackend.services;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UniteEnseignementDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;

import java.util.List;
import java.util.Set;

public interface UniteEnseignementService {
    List<UniteEnseignement> getAllUE();

    Set<UniteEnseignement> getAllUEByPromotions(String codeFormation);
}
