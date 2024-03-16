package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import org.springframework.stereotype.Service;

@Service
public interface EtudiantService {
  
    void deleteEtudiant(String noEtudiant);
    void registerEtudiant(EtudiantDTO etudiantDTO);
    EtudiantDTO updateEtudiant(String noEtudiant, EtudiantDTO etudiantDTO);

}