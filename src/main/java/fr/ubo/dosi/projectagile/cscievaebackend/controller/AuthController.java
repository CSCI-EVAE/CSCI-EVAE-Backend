package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.AuthRequestDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.JwtResponseDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UserDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.User;
import fr.ubo.dosi.projectagile.cscievaebackend.security.JwtService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController

public class AuthController {

    Logger logger  = LoggerFactory.getLogger(AuthController.class) ;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService userService;


    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, userService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/api/v1/register")
    public String registerUser(@RequestBody User registrationRequest) {
        userService.registerNewUser(registrationRequest);
        return "User registration successful";
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/api/v1/login")
    public ResponseEntity<ApiResponse<JwtResponseDTO>> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        logger.info("authentication : " + authentication);
        if(authentication.isAuthenticated()){

            return ResponseEntity.ok(new ApiResponse<>(true, "success", new JwtResponseDTO(jwtService.GenerateToken(authRequestDTO.getUsername()), new UserDTO(userService.getUserByUsername(authRequestDTO.getUsername())))));
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping/admin")
    public String test() {
        try {
            return "Welcome to the world of Spring Boot Role Based Authorization : ADMIN";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PreAuthorize("hasAnyAuthority('SECRETAIRE')")
    @GetMapping("/ping/secretaire")
    public String test2() {
        try {
            return "Welcome to the world of Spring Boot Role Based Authorization SECRETAIRE";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // add a method to display the actual user that is logged in

    @GetMapping("/api/v1/user/actual")
    public ResponseEntity<ApiResponse<User>> getActualUser() {
        //
        return ResponseEntity.ok(new ApiResponse<>(true, "success", userService.getActualUser()));
    }
}
