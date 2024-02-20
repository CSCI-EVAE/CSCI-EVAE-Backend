package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;

public interface AuthentificationService {


    Authentification getAuhtentification(String username);
}
