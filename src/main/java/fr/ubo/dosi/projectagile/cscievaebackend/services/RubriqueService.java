package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;

import java.util.List;

public interface RubriqueService {

    Rubrique creerRubrique(Rubrique rubrique);

    Rubrique getRubriqueById(Long id) throws ResourceNotFoundException;

    Rubrique updateRubrique(Long id, Rubrique rubrique) throws ResourceNotFoundException;


    List<Rubrique> getAllRubrique();

    void deleteRubrique(Long id) throws ResourceNotFoundException;

    List<Rubrique> getRubriqueByType(String type);

}
