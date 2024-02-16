package fr.ubo.dosi.projectagile.cscievaebackend.Service;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.QualificatifServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class QualificatifServiceImplTest {

    @Mock
    private QualificatifRepository qualificatifRepository;

    @InjectMocks
    private QualificatifServiceImpl qualificatifService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllQualificatifs() {
        // Arrange
        List<Qualificatif> qualificatifs = new ArrayList<>();
        when(qualificatifRepository.findAll()).thenReturn(qualificatifs);

        // Act
        List<Qualificatif> result = qualificatifService.getAllQualificatifs();

        // Assert
        assertEquals(qualificatifs, result);
        verify(qualificatifRepository, times(1)).findAll();
    }

}