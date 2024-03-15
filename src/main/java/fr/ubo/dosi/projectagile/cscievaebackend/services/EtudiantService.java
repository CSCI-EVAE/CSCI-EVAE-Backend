package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import org.springframework.stereotype.Service;

@Service
public interface EtudiantService {
    void registerEtudiant(EtudiantDTO etudiantDTO);
}