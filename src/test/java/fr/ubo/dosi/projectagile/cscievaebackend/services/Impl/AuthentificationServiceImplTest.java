package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class AuthentificationServiceImplTest {
    @InjectMocks
    private AuthentificationServiceImpl authentificationService;
    @Mock
    private AuthentificationRepository authentificationRepository;
    @Test
    public void testGetAuthentification() {
        Authentification authentification = new Authentification();
        authentification.setLoginConnection("testUser");
        when(authentificationRepository.findByLoginConnection("testUser")).thenReturn(authentification);
        Authentification result = authentificationService.getAuhtentification("testUser");
        assertEquals(authentification.getLoginConnection(), result.getLoginConnection());
    }
}