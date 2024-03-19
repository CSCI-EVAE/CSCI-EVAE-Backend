package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EtudiantRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EtudiantMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;

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
    private static final Logger logger = Logger.getLogger(EtudiantServiceImpl.class.getName());

    @Transactional
    @Override
    public void deleteEtudiant(String noEtudiant) {
        Authentification authentification = authentificationRepository.findByNoEtudiant_NoEtudiant(noEtudiant).orElse(null);
        if (authentification == null) {
            logger.severe("L'authentification de cet étudiant n'a pas été trouvée.");
            return;
        }
        etudiantRepository.delete(authentification.getNoEtudiant());
        authentificationRepository.delete(authentification);
    }

    @Override
    public void registerEtudiant(EtudiantDTO etudiantDTO) {
        String login = etudiantDTO.getEmail();
        String password = "dosi";
        Etudiant existingEtudiant = etudiantRepository.findByNoEtudiant(etudiantDTO.getNoEtudiant());
        if (existingEtudiant != null) {
            throw new IllegalArgumentException("Un étudiant avec le même numéro d'etudiant existe déjà.");
        }
        Authentification authentification = new Authentification();
        authentification.setRole("ETU");
        authentification.setLoginConnection(login);
        authentification.setMotPasse(password);
        userService.registerNewUser(authentification);
        Etudiant etudiant = etudiantMapper.etudiantDTOToEtudiant(etudiantDTO);
        Promotion promotion = promotionRepository.findByPromotionId(etudiantDTO.getCodeFormation(), etudiantDTO.getAnneeUniversitaire());
        if (promotion.getEtudiants().size() >= promotion.getNbMaxEtudiant()) {
            throw new IllegalStateException("Impossible d'ajouter l'étudiant. La promotion est pleine.");
        }
        etudiant.setPromotion(promotion);
        Etudiant etu = etudiantRepository.save(etudiant);
        Authentification auth = authentificationRepository.findByLoginConnection(login);
        auth.setNoEtudiant(etu);
        authentificationRepository.save(auth);
    }

    @Override
    public EtudiantDTO updateEtudiant(String noEtudiant, EtudiantDTO etudiantDTO) {
        Etudiant existingEtudiant = etudiantRepository.findById(noEtudiant).orElse(null);
        if (existingEtudiant == null) {
            return null;
        }
        etudiantMapper.updateEtudiantFromDTO(etudiantDTO, existingEtudiant);

        Etudiant updatedEtudiant = etudiantRepository.save(existingEtudiant);
        return etudiantMapper.etudiantToEtudiantDTO(updatedEtudiant);
    }

}