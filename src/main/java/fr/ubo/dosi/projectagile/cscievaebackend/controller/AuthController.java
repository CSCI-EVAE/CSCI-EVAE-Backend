package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EtudiantService;
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
    private final EtudiantService etudiantService;


    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, userService userService, EtudiantService etudiantService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.etudiantService = etudiantService;
    }

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

}