package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    @Autowired
    private AuthentificationRepository ar;
    @Override
    public Authentification getAuhtentification(String username) {
        try {
            return ar.findByLoginConnection(username);
        } catch ( Exception e){
            throw new RuntimeException("Authentification invalide");
        }
    }
}
