package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import static org.junit.jupiter.api.Assertions.*;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RubriqueQuestionImplTest {

    @InjectMocks
    private RubriqueQuestionImpl rubriqueService;

    @Mock
    private RubriqueRepository rubriqueRepository;

    @Test
    public void testCreerRubrique() {
        Rubrique rubrique = new Rubrique();
        rubrique.setId(1);
        rubrique.setType("TypeTest");
        rubrique.setDesignation("DesignationTest");
        rubrique.setOrdre(1L);

        Enseignant enseignant = new Enseignant();
        enseignant.setId((short) 1);
        rubrique.setNoEnseignant(enseignant);

        when(rubriqueRepository.save(any(Rubrique.class))).thenReturn(rubrique);

        Rubrique savedRubrique = rubriqueService.creerRubrique(rubrique);

        assertNotNull(savedRubrique);
        assertEquals(1, savedRubrique.getId());
        assertEquals("TypeTest", savedRubrique.getType());
        assertEquals("DesignationTest", savedRubrique.getDesignation());
        assertEquals(1L, (long) savedRubrique.getOrdre());
        assertEquals(enseignant.getId(), savedRubrique.getNoEnseignant().getId());
    }

    @Test
    public void testGetRubriqueById_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Rubrique rubrique = new Rubrique();
        rubrique.setId(id);

        when(rubriqueRepository.findById(id.longValue())).thenReturn(Optional.of(rubrique));

        Rubrique result = rubriqueService.getRubriqueById(id.longValue());

        assertEquals(id, result.getId());
    }

    @Test
    public void testGetRubriqueById_NotFound() {
        Long id = 1L;

        when(rubriqueRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rubriqueService.getRubriqueById(id));
    }

    @Test
    public void testUpdateRubrique_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Rubrique existingRubrique = new Rubrique();
        existingRubrique.setId(id);
        existingRubrique.setType("ExistingType");
        existingRubrique.setDesignation("ExistingDesignation");
        existingRubrique.setOrdre(1L);

        Rubrique modifiedRubrique = new Rubrique();
        modifiedRubrique.setType("ModifiedType");
        modifiedRubrique.setDesignation("ModifiedDesignation");
        modifiedRubrique.setOrdre(2L);

        when(rubriqueRepository.findById(id.longValue())).thenReturn(Optional.of(existingRubrique));
        when(rubriqueRepository.save(any(Rubrique.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rubrique updatedRubrique = rubriqueService.updateRubrique(id.longValue(), modifiedRubrique);

        assertNotNull(updatedRubrique);
        assertEquals(id, updatedRubrique.getId());
        assertEquals("ModifiedType", updatedRubrique.getType());
        assertEquals("ModifiedDesignation", updatedRubrique.getDesignation());
        assertEquals(2L, (long) updatedRubrique.getOrdre()); // Conversion de Long à long pour la comparaison
    }

    @Test
    public void testUpdateRubrique_NotFound() {
        Long id = 1L;
        when(rubriqueRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> rubriqueService.updateRubrique(id, new Rubrique()));
    }

    @Test
    public void testGetRubriqueByType() {
       /* String type = "TypeTest";
        List<Rubrique> expectedRubriques = new ArrayList<>();

        when(rubriqueRepository.findAllByType(type)).thenReturn(expectedRubriques);

        List<Rubrique> result = rubriqueService.getRubriqueByType(type);

        assertEquals(expectedRubriques.size(), result.size());
 */   }

    @Test
    public void testGetAllRubrique() {
        /*List<Rubrique> expectedRubriques = new ArrayList<>();

        when(rubriqueRepository.findAll()).thenReturn(expectedRubriques);

        List<Rubrique> result = rubriqueService.getAllRubrique();

        assertEquals(expectedRubriques.size(), result.size());
   */ }

    @Test
    public void testDeleteRubrique_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Rubrique existingRubrique = new Rubrique();
        existingRubrique.setId(id);

        when(rubriqueRepository.findById(id.longValue())).thenReturn(Optional.of(existingRubrique));
        doNothing().when(rubriqueRepository).deleteById(id.longValue());

        assertDoesNotThrow(() -> rubriqueService.deleteRubrique(id.longValue()));
    }

    @Test
    public void testDeleteRubrique_NotFound() {
        Long id = 1L;

        when(rubriqueRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rubriqueService.deleteRubrique(id));
    }
}
