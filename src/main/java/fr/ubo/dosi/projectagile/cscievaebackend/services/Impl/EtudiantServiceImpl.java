package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EtudiantRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import jakarta.transaction.Transactional;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private AuthentificationRepository authentificationRepository;

    private static final Logger logger = Logger.getLogger(EtudiantServiceImpl.class.getName());

    @Transactional
    @Override
    public void deleteEtudiant(String noEtudiant) {
        try {
            Authentification authentification = authentificationRepository.findByNoEtudiant_NoEtudiant(noEtudiant).orElseThrow(() -> new IllegalArgumentException("Cet Etudiant n'existe pas"));
            etudiantRepository.delete(authentification.getNoEtudiant());
            authentificationRepository.delete(authentification);
        } catch (NoSuchElementException e) {
            logger.severe("Cet Etudiant n'existe pas");
        }
    }
}