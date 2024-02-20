package fr.ubo.dosi.projectagile.cscievaebackend.controller;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class RubriqueQuestionControllerTest {

    @Mock
    private RubriqueQuestionService rubriqueQuestionService;

    @InjectMocks
    private RubriqueQuestionController rubriqueQuestionController;

    @Test
    public void testGetRubriqueQuestionById() throws ResourceNotFoundException {
        Long idQuestion = 1L;
        Long idRubrique = 2L;
        RubriqueQuestionId rubriqueQuestionId = new RubriqueQuestionId(idQuestion, idRubrique);
        RubriqueQuestionDTO rubriqueQuestionDTO = new RubriqueQuestionDTO();
        when(rubriqueQuestionService.getRubriqueQuestionById(rubriqueQuestionId)).thenReturn(rubriqueQuestionDTO);
        ResponseEntity<ApiResponse<RubriqueQuestionDTO>> response = rubriqueQuestionController.getRubriqueQuestionById(idQuestion, idRubrique);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubriqueQuestionDTO), response.getBody());
        verify(rubriqueQuestionService, times(1)).getRubriqueQuestionById(rubriqueQuestionId);
    }
    @Test
    public void testGetRubriqueQuestionById_NotFound() throws ResourceNotFoundException {
        Long idQuestion = 1L;
        Long idRubrique = 2L;
        RubriqueQuestionId rubriqueQuestionId = new RubriqueQuestionId(idQuestion, idRubrique);
        when(rubriqueQuestionService.getRubriqueQuestionById(rubriqueQuestionId)).thenReturn(null);
        ResponseEntity<ApiResponse<RubriqueQuestionDTO>> response = rubriqueQuestionController.getRubriqueQuestionById(idQuestion, idRubrique);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponse.error("Rubrique Question not found", null), response.getBody());
        verify(rubriqueQuestionService, times(1)).getRubriqueQuestionById(rubriqueQuestionId);
    }

    @Test
    public void testAddRubriqueQuestion() {
        RubriqueQuestionDTO rubriqueQuestionToAdd = new RubriqueQuestionDTO();
        when(rubriqueQuestionService.addRubriqueQuestion(any(RubriqueQuestionDTO.class))).thenReturn(rubriqueQuestionToAdd);
        ResponseEntity<ApiResponse<RubriqueQuestionDTO>> response = rubriqueQuestionController.addRubriqueQuestion(rubriqueQuestionToAdd);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ApiResponse.ok(rubriqueQuestionToAdd), response.getBody());
        verify(rubriqueQuestionService, times(1)).addRubriqueQuestion(rubriqueQuestionToAdd);
    }

    @Test
    public void testDeleteRubriqueQuestion() {
        RubriqueQuestion rubriqueQuestionToDelete = new RubriqueQuestion();
        ResponseEntity<ApiResponse<Void>> response = rubriqueQuestionController.deleteRubriqueQuestion(rubriqueQuestionToDelete);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(null), response.getBody());
        verify(rubriqueQuestionService, times(1)).deleteRubriqueQuestion(rubriqueQuestionToDelete);
    }
    @Test
    public void testDeleteRubriqueQuestion_Error() {
        RubriqueQuestion rubriqueQuestionToDelete = new RubriqueQuestion();
        doThrow(RuntimeException.class).when(rubriqueQuestionService).deleteRubriqueQuestion(rubriqueQuestionToDelete);
        ResponseEntity<ApiResponse<Void>> response = rubriqueQuestionController.deleteRubriqueQuestion(rubriqueQuestionToDelete);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ApiResponse.error("Error deleting Rubrique Question", null), response.getBody());
        verify(rubriqueQuestionService, times(1)).deleteRubriqueQuestion(rubriqueQuestionToDelete);
    }
}
