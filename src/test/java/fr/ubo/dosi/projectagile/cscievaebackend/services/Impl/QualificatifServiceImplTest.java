package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QualificatifServiceImplTest {

    @InjectMocks
    private QualificatifServiceImpl qualificatifService;

    @Mock
    private QualificatifRepository qualificatifRepository;

    @Test
    public void testCreateQualificatif() {
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(1);
        qualificatif.setMaximal("MaximalTest");
        qualificatif.setMinimal("MinimalTest");
        when(qualificatifRepository.save(any(Qualificatif.class))).thenReturn(qualificatif);
        Qualificatif savedQualificatif = qualificatifService.createQualificatif(qualificatif);
        assertNotNull(savedQualificatif);
        assertEquals(1, savedQualificatif.getId());
        assertEquals("MaximalTest", savedQualificatif.getMaximal());
        assertEquals("MinimalTest", savedQualificatif.getMinimal());
    }

    @Test
    public void testGetAllQualificatifs() {
        List<Qualificatif> expectedQualificatifs = new ArrayList<>();
        when(qualificatifRepository.findAll()).thenReturn(expectedQualificatifs);
        List<Qualificatif> result = qualificatifService.getAllQualificatifs();
        assertEquals(expectedQualificatifs.size(), result.size());
    }

    @Test
    public void testGetQualificatifById_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(id);
        when(qualificatifRepository.findById(id.longValue())).thenReturn(Optional.of(qualificatif));
        Optional<Qualificatif> result = qualificatifService.getQualificatifById(id.longValue());
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

     @Test
     public void testGetQualificatifById_NotFound() {
      long id = 1;
      when(qualificatifRepository.findById(id)).thenReturn(Optional.empty());
      assertThrows(ResourceNotFoundException.class, () -> qualificatifService.getQualificatifById(id));
    }
    @Test
    public void testUpdateQualificatif_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Qualificatif qualificatifExistant = new Qualificatif();
        qualificatifExistant.setId(id);
        qualificatifExistant.setMinimal("MinimalExistant");
        qualificatifExistant.setMaximal("MaximalExistant");
        Qualificatif qualificatifModifie = new Qualificatif();
        qualificatifModifie.setMinimal("NouveauMinimal");
        qualificatifModifie.setMaximal("NouveauMaximal");
        when(qualificatifRepository.findById(id.longValue())).thenReturn(Optional.of(qualificatifExistant));
        when(qualificatifRepository.save(Mockito.any(Qualificatif.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Qualificatif updatedQualificatif = qualificatifService.updateQualificatif(id.longValue(), qualificatifModifie);
        assertNotNull(updatedQualificatif);
        assertEquals(id, updatedQualificatif.getId());
        assertEquals("NouveauMinimal", updatedQualificatif.getMinimal());
        assertEquals("NouveauMaximal", updatedQualificatif.getMaximal());
    }

    @Test
    public void testDeleteQualificatif_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Qualificatif qualificatifExistant = new Qualificatif();
        qualificatifExistant.setId(id);
        when(qualificatifRepository.findById(id.longValue())).thenReturn(Optional.of(qualificatifExistant));
        Mockito.doNothing().when(qualificatifRepository).deleteById(id.longValue());
        assertDoesNotThrow(() -> qualificatifService.deleteQualificatif(id.longValue()));
    }




}