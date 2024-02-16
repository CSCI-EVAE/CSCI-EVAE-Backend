package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/enseignant/ue")
public class UniteEnseignementController {

    private final userService userService;

    @Autowired
    public UniteEnseignementController(userService userService) {
        this.userService = userService;
    }
    @GetMapping("/all")
    public ApiResponse<Set<UniteEnseignement>> getAll(){
        Authentification authentication = userService.getCurrentUser();
        Set<UniteEnseignement> uniteEnseignements = authentication.getNoEnseignant().getUniteEnseignements();
        return ApiResponse.ok(uniteEnseignements);
    }
}
