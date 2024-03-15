package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EtudiantService {
    void registerEtudiant(EtudiantDTO etudiantDTO);
}