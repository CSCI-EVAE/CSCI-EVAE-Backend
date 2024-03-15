package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.AuthRequestDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.JwtResponseDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UserDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.security.JwtService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController

public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final userService userService;
    @Autowired
    private EtudiantService etudiantService;


    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, userService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * This method is responsible for authenticating a user and generating a JWT token.
     * It is mapped to the "/api/v1/login" endpoint and responds to HTTP POST requests.
     *
     * @param authRequestDTO This is a request body object that contains the username and password for authentication.
     * @return ResponseEntity<ApiResponse < JwtResponseDTO>> This returns a response entity that contains an API response.
     * The API response includes a status, message, and a JWT response DTO that contains the generated JWT token and user details.
     * If the authentication is successful, it returns a 200 OK response with the JWT token and user details.
     * If the authentication fails due to incorrect credentials, it returns a 500 Internal Server Error response with an appropriate message.
     * If the user does not exist, it throws a UsernameNotFoundException.
     */
    @PostMapping("/api/v1/login")
    public ResponseEntity<?> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            if (userService.getUserByUsername(authRequestDTO.getUsername()) == null) {
                return ApiResponse.error("L'utilisateur n'existe pas");
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "La connexion a réussi", new JwtResponseDTO(jwtService.GenerateToken(authRequestDTO.getUsername()), new UserDTO(userService.getUserByUsername(authRequestDTO.getUsername())))));
            } else {
                return ApiResponse.error("Nom d'utilisateur ou mot de passe incorrect");
            }
        } catch (BadCredentialsException e) {
            return ApiResponse.error("Nom d'utilisateur ou mot de passe incorrect");
        }
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<?> registerUser(@RequestBody Authentification userDTO) {
        return ApiResponse.ok(userService.registerUser(userDTO));
    }
    @PostMapping("/register-etudiant")
    public ResponseEntity<?> registerEtudiant(@Validated @RequestBody EtudiantDTO etudiantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.error("Une erreur s'est produite lors de la création du qualificatif", bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
        }
        if (userService.getUserByUsername(etudiantDTO.getEmail()) != null) {
            return ApiResponse.error("L'étudiant existe déjà");
        }
        etudiantService.registerEtudiant(etudiantDTO);
        return ApiResponse.ok("L'étudiant a été enregistré avec succès");
    }

}