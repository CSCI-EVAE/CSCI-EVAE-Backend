package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.AuthRequestDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.JwtResponseDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UserDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.security.JwtService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController

public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final userService userService;


    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, userService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * Authentifie l'utilisateur et génère un token JWT.
     *
     * @param authRequestDTO L'objet contenant les informations d'authentification de l'utilisateur (nom d'utilisateur et mot de passe).
     * @return ApiResponse contenant un objet JwtResponseDTO si l'authentification est réussie,
     * sinon une exception UsernameNotFoundException est lancée.
     */
    @PostMapping("/api/v1/login")
    public ResponseEntity<ApiResponse<JwtResponseDTO>> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        logger.info("authentication : " + authentication);
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "La connexion a réussi"
                    , new JwtResponseDTO(jwtService.GenerateToken(authRequestDTO.getUsername()), new UserDTO(userService.getUserByUsername(authRequestDTO.getUsername())))));
        } else {
            throw new UsernameNotFoundException("Mot de passe ou nom d'utilisateur incorrect");
        }
    }

    @PostMapping("/api/v1/register")
    public ApiResponse<UserDTO> registerUser(@RequestBody Authentification userDTO) {
        return ApiResponse.ok(userService.registerUser(userDTO));
    }

}
