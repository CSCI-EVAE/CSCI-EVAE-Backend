package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EtudiantMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EtudiantRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtudiantServiceImpl implements EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private userService userService;

    @Autowired
    private AuthentificationRepository authentificationRepository;

    @Autowired
    private EtudiantMapper etudiantMapper;

    @Override
    public void registerEtudiant(EtudiantDTO etudiantDTO) {
        String login = etudiantDTO.getEmail();
        String password = "dosi";
        Authentification authentification = new Authentification();
        authentification.setRole("ETU");
        authentification.setLoginConnection(login);
        authentification.setMotPasse(password);
        userService.registerNewUser(authentification);
        Etudiant etudiant = etudiantMapper.etudiantDTOToEtudiant(etudiantDTO);
        Promotion promotion = promotionRepository.findByPromotionId(etudiantDTO.getCodeFormation(), etudiantDTO.getAnneeUniversitaire());
        etudiant.setPromotion(promotion);
        Etudiant etu = etudiantRepository.save(etudiant);
        Authentification auth = authentificationRepository.findByLoginConnection(login);
        auth.setNoEtudiant(etu);
        authentificationRepository.save(auth);
    }
}