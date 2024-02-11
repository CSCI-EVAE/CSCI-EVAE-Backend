package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Role;
import fr.ubo.dosi.projectagile.cscievaebackend.model.User;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.UserRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.AuthRequestDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public void registerNewUser(User registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        // check if all roles exists in the database
        for (Role role : registrationRequest.getRoles()) {
            if (roleService.getRoleById(role.getId()).isEmpty()) {
                throw new RuntimeException("Role does not exist");
            }
        }
        //get the password from the request and encode it
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        registrationRequest.setPassword(encodedPassword);
        userRepository.save(registrationRequest);
    }

    public User getActualUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
