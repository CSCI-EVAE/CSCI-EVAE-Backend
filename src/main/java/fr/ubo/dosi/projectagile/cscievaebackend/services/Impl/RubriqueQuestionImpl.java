package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.EmptyRequestBodyException;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RubriqueQuestionImpl implements RubriqueService {

    @Autowired
    private RubriqueRepository rubriqueRepository;

    public Rubrique creerRubrique(Rubrique rubrique){
       return rubriqueRepository.save(rubrique);
    }

    public Rubrique getRubriqueById(Long id) throws ResourceNotFoundException {
        return rubriqueRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rubrique non trouver de cet id: "+ id));
    }

    public List<Rubrique> getRubriqueByType(String type) {
        return rubriqueRepository.findAllByType(type);
    }

    public Rubrique updateRubrique(Long id, Rubrique rubrique) throws ResourceNotFoundException {

        Optional<Rubrique> rubriqueExistant = rubriqueRepository.findById(id);

        if(rubriqueExistant.isPresent()){
            Rubrique rubriqueUpdate = rubriqueExistant.get();
            rubriqueUpdate.setDesignation(rubrique.getDesignation());
            rubriqueUpdate.setOrdre(rubrique.getOrdre());
            rubriqueUpdate.setType(rubrique.getType());
            rubriqueUpdate.setNoEnseignant(rubrique.getNoEnseignant());
            return rubriqueRepository.save(rubriqueUpdate);
        }else{
            throw new ResourceNotFoundException("La rubrique est inexistante avec ce id : "+ id);
        }
    }

    public List<Rubrique> getAllRubrique(){
        return rubriqueRepository.findAll();
    }

    @Override
    public void deleteRubrique(Long id) throws ResourceNotFoundException {
        Optional<Rubrique> rubriqueOptional = rubriqueRepository.findById(id);
        if (rubriqueOptional == null) {
            throw new EmptyRequestBodyException("Empty request body");
        }
        if (rubriqueOptional.isEmpty()) {
            throw new ResourceNotFoundException("Rubrique not found with id: " + id);
        }
        rubriqueRepository.deleteById(id);
    }

}
