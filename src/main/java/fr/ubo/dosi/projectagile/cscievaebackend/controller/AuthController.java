package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.AuthRequestDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.JwtResponseDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UserDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.security.JwtService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@Tag(name = "Authentification", description = "Authentification API")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final userService userService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, userService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }
    @Operation(summary = "Authenticate and get token", description = "Authenticate and get token")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Authentication successful", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = JwtResponseDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })

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

    @Operation(summary = "Register a new user", description = "Register a new user")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User registered successfully", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PostMapping("/api/v1/register")
    public ResponseEntity<?> registerUser(@RequestBody Authentification userDTO) {
        return ApiResponse.ok(userService.registerUser(userDTO));
    }

}