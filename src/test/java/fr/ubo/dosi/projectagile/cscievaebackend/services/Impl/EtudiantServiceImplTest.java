package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EtudiantMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EtudiantRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {
    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;
    @Mock
    private PromotionRepository promotionRepository;
    @Mock
    private userService userService;
    @Mock
    private AuthentificationRepository authentificationRepository;
    @Mock
    private EtudiantMapper etudiantMapper;

    @Test
    public void testDeleteEtudiant_Success() {
        String noEtudiant = "12345";
        Authentification authentification = new Authentification();
        when(authentificationRepository.findByNoEtudiant_NoEtudiant(noEtudiant)).thenReturn(Optional.of(authentification));
        etudiantService.deleteEtudiant(noEtudiant);
        verify(etudiantRepository).delete(authentification.getNoEtudiant());
        verify(authentificationRepository).delete(authentification);
    }
    @Test
    public void testDeleteEtudiant_NotFound() {
        String noEtudiant = "12345";
        when(authentificationRepository.findByNoEtudiant_NoEtudiant(noEtudiant)).thenReturn(Optional.empty());
        etudiantService.deleteEtudiant(noEtudiant);
        verify(etudiantRepository, never()).delete(any());
        verify(authentificationRepository, never()).delete(any());
    }
    @Test
    public void testRegisterEtudiant_Success() {
        // Création d'un objet EtudiantDTO factice pour le test
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setNoEtudiant("12345");
        etudiantDTO.setEmail("test@example.com");
        etudiantDTO.setCodeFormation("formation123");
        etudiantDTO.setAnneeUniversitaire("2024");

        // Définition du comportement simulé des mocks
        when(etudiantRepository.findByNoEtudiant(anyString())).thenReturn(null); // Aucun étudiant avec le même numéro
        when(promotionRepository.findByPromotionId(anyString(), anyString())).thenReturn(new Promotion()); // Promotion existante
        when(promotionRepository.findByPromotionId(eq("formation123"), eq("2024"))).thenReturn(createFullPromotion()); // Promotion pleine

        // Appel de la méthode à tester
        assertThrows(IllegalStateException.class, () -> etudiantService.registerEtudiant(etudiantDTO));

        // Vérification des interactions avec les mocks
        verify(userService).registerNewUser(any(Authentification.class));
        verify(etudiantRepository, never()).save(any(Etudiant.class));
    }

    @Test
    public void testUpdateEtudiant_Success() {
        // Création d'un objet EtudiantDTO factice pour le test
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setNoEtudiant("12345");
        etudiantDTO.setNom("Nom Updated");
        etudiantDTO.setPrenom("Prenom Updated");

        // Création d'un étudiant factice pour le test
        Etudiant existingEtudiant = new Etudiant();
        existingEtudiant.setNoEtudiant("12345");
        existingEtudiant.setNom("Nom Original");
        existingEtudiant.setPrenom("Prenom Original");

        // Définition du comportement simulé des mocks
        when(etudiantRepository.findById(anyString())).thenReturn(Optional.of(existingEtudiant));

        // Appel de la méthode à tester
        EtudiantDTO updatedEtudiantDTO = etudiantService.updateEtudiant("12345", etudiantDTO);

        // Vérification des résultats
        assertNotNull(updatedEtudiantDTO);
        assertEquals("Nom Updated", updatedEtudiantDTO.getNom());
        assertEquals("Prenom Updated", updatedEtudiantDTO.getPrenom());
    }

    // Méthode utilitaire pour créer une promotion pleine
    private Promotion createFullPromotion() {
        Promotion promotion = new Promotion();
        promotion.setNbMaxEtudiant((short)10); // Nombre maximal d'étudiants
        // Ajouter des étudiants jusqu'au maximum
        for (int i = 0; i < 10; i++) {
            Etudiant etudiant = new Etudiant();
            promotion.getEtudiants().add(etudiant);
        }
        return promotion;
    }


}

/*@Test
    public void testRegisterEtudiant_Success() {
        // Configuration du stub pour la méthode findByPromotionId

        // Configuration du stub pour la méthode findByPromotionId
        Promotion promotion = new Promotion();
        promotion.setNbMaxEtudiant((short) 10); // Définissez le nombre maximum d'étudiants sur une valeur non nulle
        when(promotionRepository.findByPromotionId(anyString(), anyString())).thenReturn(promotion);

        // Préparation des données d'entrée
        EtudiantDTO etudiantDTO = new EtudiantDTO();
        etudiantDTO.setNoEtudiant("20324694");
        etudiantDTO.setNom("Dupont");
        etudiantDTO.setPrenom("Jean");
        etudiantDTO.setSexe("M");
        etudiantDTO.setDateNaissance(LocalDate.of(1995, 5, 15));
        etudiantDTO.setLieuNaissance("Paris");
        etudiantDTO.setNationalite("Française");
        etudiantDTO.setTelephone("0123456789");
        etudiantDTO.setMobile("0678901234");
        etudiantDTO.setEmail("jean.dupont@example.com");
        etudiantDTO.setEmailUbo("jean.dupont@univ-brest.fr");
        etudiantDTO.setAdresse("123, Rue de la Paix");
        etudiantDTO.setCodePostal("75001");
        etudiantDTO.setVille("Paris");
        etudiantDTO.setPaysOrigine("Fra");
        etudiantDTO.setUniversiteOrigine("Uni");
        etudiantDTO.setGroupeTp(1L);
        etudiantDTO.setGroupeAnglais(2L);
        etudiantDTO.setCodeFormation("M2DOSI"); // Promotion
        etudiantDTO.setAnneeUniversitaire("2013-2014");

        // Appel de la méthode à tester
        etudiantService.registerEtudiant(etudiantDTO);

        // Vérification des interactions avec les mocks si nécessaire
        verify(promotionRepository, times(1)).findByPromotionId(anyString(), anyString());
        // Ajoutez d'autres vérifications au besoin
   }*/