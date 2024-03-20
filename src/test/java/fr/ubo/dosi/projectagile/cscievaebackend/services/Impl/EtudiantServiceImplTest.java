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
//    @Test
//    public void testRegisterEtudiant_Success() {
//        EtudiantDTO etudiantDTO = new EtudiantDTO();
//        etudiantDTO.setNoEtudiant("12345");
//        etudiantDTO.setEmail("test@example.com");
//        etudiantDTO.setCodeFormation("formation123");
//        etudiantDTO.setAnneeUniversitaire("2024");
//
//        when(etudiantRepository.findByNoEtudiant(anyString())).thenReturn(null);
//        when(promotionRepository.findByPromotionId(anyString(), anyString())).thenReturn(new Promotion());
//        when(promotionRepository.findByPromotionId(eq("formation123"), eq("2024"))).thenReturn(createFullPromotion());
//        assertThrows(IllegalStateException.class, () -> etudiantService.registerEtudiant(etudiantDTO));
//        verify(userService).registerNewUser(any(Authentification.class));
//        verify(etudiantRepository, never()).save(any(Etudiant.class));
//    }
//
//    @Test
//    public void testUpdateEtudiant_Success() {
//        EtudiantDTO etudiantDTO = new EtudiantDTO();
//        etudiantDTO.setNoEtudiant("12345");
//        etudiantDTO.setNom("Nom Updated");
//        etudiantDTO.setPrenom("Prenom Updated");
//        Etudiant existingEtudiant = new Etudiant();
//        existingEtudiant.setNoEtudiant("12345");
//        existingEtudiant.setNom("Nom Original");
//        existingEtudiant.setPrenom("Prenom Original");
//        when(etudiantRepository.findById(anyString())).thenReturn(Optional.of(existingEtudiant));
//        EtudiantDTO updatedEtudiantDTO = etudiantService.updateEtudiant("12345", etudiantDTO);
//        assertNotNull(updatedEtudiantDTO);
//        assertEquals("Nom Updated", updatedEtudiantDTO.getNom());
//        assertEquals("Prenom Updated", updatedEtudiantDTO.getPrenom());
//    }

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