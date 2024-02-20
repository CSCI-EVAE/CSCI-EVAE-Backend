package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QualificatifControllerTest {
    @Mock
    private QualificatifService qualificatifService;
    @Mock
    private QuestionService questionService;
    @InjectMocks
    private QualificatifController qualificatifController;

    @Test
    public void testGetQualificatifs() {
        List<Qualificatif> qualificatifs = new ArrayList<>();
        qualificatifs.add(new Qualificatif());
        when(qualificatifService.getAllQualificatifs()).thenReturn(qualificatifs);
        ApiResponse<List<Qualificatif>> response = qualificatifController.getQualificatifs();
        assertEquals(ApiResponse.ok(qualificatifs), response);
        verify(qualificatifService, times(1)).getAllQualificatifs();
    }

    @Test
    public void testGetQualificatifById() throws ResourceNotFoundException {
        Integer id = 1;
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(id);
        when(qualificatifService.getQualificatifById(id.longValue())).thenThrow(ResourceNotFoundException.class);
        ApiResponse<Qualificatif> response = qualificatifController.getQualificatifById(id.longValue());
        assertEquals(ApiResponse.error("Error retrieving Qualificatif", null), response);
        verify(qualificatifService, times(1)).getQualificatifById(id.longValue());
    }

    @Test
    public void testGetQualificatifById_NotFound() throws ResourceNotFoundException {
        long id = 1;
        when(qualificatifService.getQualificatifById(id)).thenThrow(ResourceNotFoundException.class);
        ApiResponse<Qualificatif> response = qualificatifController.getQualificatifById(id);
        assertEquals(ApiResponse.error("Error retrieving Qualificatif", null), response);
        verify(qualificatifService, times(1)).getQualificatifById(id);
    }
    @Test
    public void testAddQualificatif() {
        Qualificatif qualificatifToAdd = new Qualificatif();
        when(qualificatifService.createQualificatif(any(Qualificatif.class))).thenReturn(qualificatifToAdd);
        ApiResponse<Qualificatif> response = qualificatifController.addQualificatif(qualificatifToAdd);
        assertEquals(ApiResponse.ok(qualificatifToAdd), response);
        verify(qualificatifService, times(1)).createQualificatif(qualificatifToAdd);
    }

    @Test
    public void testUpdateQualificatif() throws ResourceNotFoundException {
        Long id = 1L;
        Qualificatif qualificatifToUpdate = new Qualificatif();
        when(qualificatifService.updateQualificatif(eq(id), any(Qualificatif.class))).thenReturn(qualificatifToUpdate);
        ApiResponse<Qualificatif> response = qualificatifController.updateQualificatif(id, qualificatifToUpdate);
        assertEquals(ApiResponse.ok(qualificatifToUpdate), response);
        verify(qualificatifService, times(1)).updateQualificatif(id, qualificatifToUpdate);
    }

    @Test
    public void testDeleteQualificatif() throws ResourceNotFoundException, SQLException {
        Long id = 1L;
        Qualificatif qualificatifToDelete = new Qualificatif();
        when(qualificatifService.getQualificatifById(id)).thenReturn(Optional.of(qualificatifToDelete));
        when(questionService.findQuestionsByQualificatifId(qualificatifToDelete)).thenReturn(Collections.emptyList());
        ApiResponse<Void> response = qualificatifController.deleteQualificatif(id);
        assertEquals(ApiResponse.ok(null), response);
        verify(qualificatifService, times(1)).deleteQualificatif(id);
    }

    @Test
    public void testDeleteQualificatif_QualificatifNotFound() throws ResourceNotFoundException, SQLException {
        Long id = 1L;
        when(qualificatifService.getQualificatifById(id)).thenReturn(Optional.empty());
        ApiResponse<Void> response = qualificatifController.deleteQualificatif(id);
        assertEquals(ApiResponse.error("Ce qualificatif n'existe pas", null), response);
    }

    @Test
    public void testDeleteQualificatif_QualificatifLinkedToQuestions() throws ResourceNotFoundException, SQLException {
        Long id = 1L;
        Qualificatif qualificatifToDelete = new Qualificatif();
        when(qualificatifService.getQualificatifById(id)).thenReturn(Optional.of(qualificatifToDelete));
        when(questionService.findQuestionsByQualificatifId(qualificatifToDelete)).thenReturn(Collections.singletonList(new Question()));
        ApiResponse<Void> response = qualificatifController.deleteQualificatif(id);
        assertEquals(ApiResponse.error("Ce qualificatif ne peut pas etre supprimer car il est lié a 1 Questions", null), response);
    }
}
