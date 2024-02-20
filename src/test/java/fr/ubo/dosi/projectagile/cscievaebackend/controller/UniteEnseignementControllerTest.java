package fr.ubo.dosi.projectagile.cscievaebackend.controller;



import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class UniteEnseignementControllerTest {

    @Mock
    private userService uuserService;

    @InjectMocks
    private UniteEnseignementController uniteEnseignementController;

    @Test
    public void testGetAll() {
        Authentification authentication = new Authentification();
        Enseignant enseignant = new Enseignant();
        Set<UniteEnseignement> uniteEnseignements = new HashSet<>();
        enseignant.setUniteEnseignements(uniteEnseignements);
        authentication.setNoEnseignant(enseignant);
        when(uuserService.getCurrentUser()).thenReturn(authentication);
        ApiResponse<Set<UniteEnseignement>> apiResponse = ApiResponse.ok(uniteEnseignements);
        ResponseEntity<ApiResponse<Set<UniteEnseignement>>> responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        ApiResponse<Set<UniteEnseignement>> response = uniteEnseignementController.getAll();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(uniteEnseignements, response.getData());
        verify(uuserService, times(1)).getCurrentUser();
    }
}
