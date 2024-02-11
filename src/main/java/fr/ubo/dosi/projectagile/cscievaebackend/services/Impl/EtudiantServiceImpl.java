package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EtudiantRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;
    // promotion repository
    @Autowired
    private PromotionRepository promotionRepository;

    // Implement methods for CRUD operations
    public Etudiant createEtudiant(Etudiant etudiant) {
        Promotion promotion = etudiant.getAnneePro();
        etudiant.setAnneePro(promotionRepository.save(promotion));
        return etudiantRepository.save(etudiant);
    }


    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Etudiant getEtudiantById(String id) throws ResourceNotFoundException {
        return etudiantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Etudiant not found for this id :: " + id));
    }

    public Etudiant updateEtudiant(String id, Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public void deleteEtudiant(String id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant getEtudiant(String number) {
        return etudiantRepository.findByNoEtudiantNat(number);
    }
}