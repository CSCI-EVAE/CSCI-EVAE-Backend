package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.mappers.EvaluationMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Authentification;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EvaluationSaveDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.services.AuthentificationService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@WebMvcTest(EvaluationController.class)
@ExtendWith(SpringExtension.class)
public class EvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthentificationService authentificationService;

    @MockBean
    private EvaluationMapper evaluationMapper;

 /*   @Test
    @WithMockUser(username = "user", authorities = {"ENS"})
    public void getAllEvaluationsTest() throws Exception {
        // Arrange
        Authentification auth = new Authentification(); // Create a mock Authentification object
        when(authentificationService.getAuhtentification(anyString())).thenReturn(auth);
        Set<Evaluation> evaluations = new HashSet<>(); // Create a mock Set of Evaluations
        when(auth.getNoEnseignant().getEvaluations()).thenReturn(evaluations);
        // Assume evaluationMapper.evaluationToEvaluationDTO() is properly mocked as well

        // Act & Assert
        mockMvc.perform(get("/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Aucune évaluation n'est disponible pour cet enseignant"))); // or whatever the expected response should be
    }*/
}
