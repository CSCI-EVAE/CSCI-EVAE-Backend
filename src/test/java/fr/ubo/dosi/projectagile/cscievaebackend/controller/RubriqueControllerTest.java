package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RubriqueControllerTest {

    @Mock
    private RubriqueService rubriqueService;

    @InjectMocks
    private RubriqueController rubriqueController;

    @Test
    public void testCreateRubrique() {
      /*  Rubrique rubriqueToAdd = new Rubrique();
        when(rubriqueService.creerRubrique(any(Rubrique.class))).thenReturn(rubriqueToAdd);
        ResponseEntity<ApiResponse<Rubrique>> response = rubriqueController.createRubrique(rubriqueToAdd);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubriqueToAdd), response.getBody());
        verify(rubriqueService, times(1)).creerRubrique(rubriqueToAdd);*/
    }

    @Test
    public void testGetAllRubrique() {
       /* List<Rubrique> rubriques = new ArrayList<>();
        rubriques.add(new Rubrique());
        when(rubriqueService.getAllRubrique()).thenReturn(rubriques);
        ResponseEntity<ApiResponse<List<Rubrique>>> response = rubriqueController.getAllRubrique();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubriques), response.getBody());
        verify(rubriqueService, times(1)).getAllRubrique();*/
    }

    @Test
    public void testGetRubriqueByType() {
   /*     String type = "exampleType";
        List<Rubrique> rubriques = new ArrayList<>();
        rubriques.add(new Rubrique());
        when(rubriqueService.getRubriqueByType(type)).thenReturn(rubriques);
        ResponseEntity<ApiResponse<List<Rubrique>>> response = rubriqueController.getRubriqueByType(type);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubriques), response.getBody());
        verify(rubriqueService, times(1)).getRubriqueByType(type);*/
    }

    @Test
    public void testGetRubriqueById() throws ResourceNotFoundException {
       /* Integer id = 1;
        Rubrique rubrique = new Rubrique();
        rubrique.setId(id);
        when(rubriqueService.getRubriqueById(id.longValue())).thenReturn(rubrique);
        ResponseEntity<ApiResponse<Rubrique>> response = rubriqueController.getRubriqueById(id.longValue());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubrique), response.getBody());
        verify(rubriqueService, times(1)).getRubriqueById(id.longValue());*/
    }

    @Test
    public void testGetRubriqueById_NotFound() throws ResourceNotFoundException {
       /* Long id = 1L;
        when(rubriqueService.getRubriqueById(id)).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<ApiResponse<Rubrique>> response = rubriqueController.getRubriqueById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponse.error("Rubrique not found", null), response.getBody());
        verify(rubriqueService, times(1)).getRubriqueById(id);*/
    }

    @Test
    public void testUpdateRubrique() throws ResourceNotFoundException {
/*        Long id = 1L;
        Rubrique rubriqueToUpdate = new Rubrique();
        when(rubriqueService.updateRubrique(eq(id), any(Rubrique.class))).thenReturn(rubriqueToUpdate);
        ResponseEntity<ApiResponse<Rubrique>> response = rubriqueController.updateRubrique(id, rubriqueToUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubriqueToUpdate), response.getBody());
        verify(rubriqueService, times(1)).updateRubrique(id, rubriqueToUpdate);*/
    }

    @Test
    public void testUpdateRubrique_NotFound() throws ResourceNotFoundException {
    /*    Long id = 1L;
        Rubrique rubriqueToUpdate = new Rubrique();
        when(rubriqueService.updateRubrique(eq(id), any(Rubrique.class))).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<ApiResponse<Rubrique>> response = rubriqueController.updateRubrique(id, rubriqueToUpdate);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponse.error("Rubrique not found", null), response.getBody());
        verify(rubriqueService, times(1)).updateRubrique(id, rubriqueToUpdate);*/
    }


}
