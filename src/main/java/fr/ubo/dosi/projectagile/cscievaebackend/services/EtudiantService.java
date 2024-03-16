package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;

public interface EtudiantService {
    Etudiant createEtudiant(Etudiant etudiant);

    void deleteEtudiant(String noEtudiant);
}