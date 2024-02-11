package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    @Autowired
    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    public Optional<Formation> getFormationById(String codeFormation) {
        return formationRepository.findById(codeFormation);
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Formation updateFormation(String codeFormation, Formation updatedFormation) {
        if (formationRepository.existsById(codeFormation)) {
            updatedFormation.setCodeFormation(codeFormation); // Ensure the code remains the same
            return formationRepository.save(updatedFormation);
        }
        return null; // Handle the case when the formation doesn't exist
    }

    public boolean deleteFormation(String codeFormation) {
        if (formationRepository.existsById(codeFormation)) {
            formationRepository.deleteById(codeFormation);
            return true;
        }
        return false; // Handle the case when the formation doesn't exist
    }
}
