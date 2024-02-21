package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.AuthentificationServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.EvaluationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EvaluationControllerTest {

    @InjectMocks
    EvaluationController evaluationController;

    @Mock
    AuthentificationServiceImpl as;

    @Mock
    EvaluationMapper evaluationMapper;
    @Mock
    EvaluationServiceImpl es;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllReturnsEvaluationsWhenExist() {
        /* when(as.getAuhtentification(anyString())).thenReturn(new Authentification());
        when(evaluationMapper.evaluationToEvaluationDTO(any())).thenReturn(new EvaluationDTO());

        ApiResponse<?> response = evaluationController.getAll(any());

        assertNotNull(response);
        assertInstanceOf(Set.class, response.getData());*/
    }

    @Test
    void getAllReturnsMessageWhenNoEvaluations() {
        /*when(as.getAuhtentification(anyString())).thenReturn(new Authentification());

        ApiResponse<?> response = evaluationController.getAll(any());

        assertNotNull(response);
        assertEquals("Aucune évaluation n'est disponible pour cet enseignant", response.getData());*/
    }

    @Test
    void getDetailsReturnsEvaluationDetailsWhenExist() {
        when(es.getEvaluationById(anyLong())).thenReturn(new Evaluation());
        when(evaluationMapper.evaluationToEvaluationDTO(any())).thenReturn(new EvaluationDTO());

        ApiResponse<EvaluationDTO> response = evaluationController.getDetails(1L);
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    @Test
    void getDetailsThrowsExceptionWhenNoEvaluation() {
        /*when(es.getEvaluationById(anyLong())).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> evaluationController.getDetails(1L));*/
    }

    @Test
    void getEvaluationsByUserReturnsEvaluationsWhenExist() {
        /*when(as.getAuhtentification(anyString())).thenReturn(new Authentification());
        when(evaluationMapper.evaluationToEvaluationDTO(any())).thenReturn(new EvaluationDTO());

        ApiResponse<Set<EvaluationDTO>> response = evaluationController.getEvaluationsByUser(any());

        assertNotNull(response);
        assertNotNull(response.getData());*/
    }

    @Test
    void getEvaluationsByUserReturnsEmptySetWhenNoEvaluations() {
      /*  when(as.getAuhtentification(anyString())).thenReturn(new Authentification());

        ApiResponse<Set<EvaluationDTO>> response = evaluationController.getEvaluationsByUser(any());

        assertNotNull(response);
        assertTrue(response.getData().isEmpty());*/
    }
}