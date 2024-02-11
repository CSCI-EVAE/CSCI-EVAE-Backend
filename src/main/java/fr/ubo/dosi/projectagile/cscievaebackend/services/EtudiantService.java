package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;

import java.util.List;

public interface EtudiantService {
    public Etudiant createEtudiant(Etudiant etudiant);

    public List<Etudiant> getAllEtudiants();

    public Etudiant getEtudiantById(String id) throws ResourceNotFoundException;

    public Etudiant updateEtudiant(String id, Etudiant etudiant);

    public void deleteEtudiant(String id) ;

    Etudiant getEtudiant(String number);
}

