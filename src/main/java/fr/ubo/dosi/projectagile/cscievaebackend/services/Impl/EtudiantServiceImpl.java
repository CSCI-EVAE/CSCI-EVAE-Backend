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
        try {
            Authentification authentification = authentificationRepository.findByNoEtudiant_NoEtudiant(noEtudiant).orElseThrow(() -> new IllegalArgumentException("Cet Etudiant n'existe pas"));
            etudiantRepository.delete(authentification.getNoEtudiant());
            authentificationRepository.delete(authentification);
        } catch (NoSuchElementException e) {
            logger.severe("Cet Etudiant n'existe pas");
        }
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
        existingEtudiant.setNom(etudiantDTO.getNom());
        existingEtudiant.setPrenom(etudiantDTO.getPrenom());
        existingEtudiant.setSexe(etudiantDTO.getSexe());
        existingEtudiant.setDateNaissance(etudiantDTO.getDateNaissance());
        existingEtudiant.setLieuNaissance(etudiantDTO.getLieuNaissance());
        existingEtudiant.setNationalite(etudiantDTO.getNationalite());
        existingEtudiant.setTelephone(etudiantDTO.getTelephone());
        existingEtudiant.setMobile(etudiantDTO.getMobile());
        existingEtudiant.setEmail(etudiantDTO.getEmail());
        existingEtudiant.setEmailUbo(etudiantDTO.getEmailUbo());
        existingEtudiant.setAdresse(etudiantDTO.getAdresse());
        existingEtudiant.setCodePostal(etudiantDTO.getCodePostal());
        existingEtudiant.setVille(etudiantDTO.getVille());
        existingEtudiant.setPaysOrigine(etudiantDTO.getPaysOrigine());
        existingEtudiant.setUniversiteOrigine(etudiantDTO.getUniversiteOrigine());
        existingEtudiant.setGroupeTp(etudiantDTO.getGroupeTp());
        existingEtudiant.setGroupeAnglais(etudiantDTO.getGroupeAnglais());
        Etudiant updatedEtudiant = etudiantRepository.save(existingEtudiant);
        return etudiantMapper.etudiantToEtudiantDTO(updatedEtudiant);
    }

}