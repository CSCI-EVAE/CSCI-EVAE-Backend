package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UserDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.AuthentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthentificationRepository userRepository;

    public void registerNewUser(Authentification registrationRequest) {
        if (userRepository.existsByLoginConnection(registrationRequest.getPseudoConnection())) {
            throw new RuntimeException("Username already exists");
        }

        //get the password from the request and encode it
        String encodedPassword = passwordEncoder.encode(registrationRequest.getMotPasse());
        registrationRequest.setMotPasse(encodedPassword);
        userRepository.save(registrationRequest);
    }


    public Authentification getUserByUsername(String username) {
        return userRepository.findByLoginConnection(username);
    }

    public Authentification getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLoginConnection(username);
    }

    public UserDTO registerUser(Authentification user) {
        registerNewUser(user);
        return new UserDTO(user);
    }
}
