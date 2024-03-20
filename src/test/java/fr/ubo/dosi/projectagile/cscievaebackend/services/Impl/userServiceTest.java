package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
@ExtendWith(MockitoExtension.class)
class userServiceTest {
    @InjectMocks
    private userService userServicee;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthentificationRepository userRepository;
    @Test
    public void testRegisterNewUser() {
        Authentification user = new Authentification();
        user.setLoginConnection("testUser");
        user.setMotPasse("password");
        when(userRepository.findByLoginConnection("testUser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        userServicee.registerNewUser(user);
        assertEquals("encodedPassword", user.getMotPasse());
    }
    @Test
    public void testGetUserByUsername() {
        Authentification user = new Authentification();
        user.setLoginConnection("testUser");
        when(userRepository.findByLoginConnection("testUser")).thenReturn(user);
        Authentification result = userServicee.getUserByUsername("testUser");
        assertEquals(user.getLoginConnection(), result.getLoginConnection());
    }
}