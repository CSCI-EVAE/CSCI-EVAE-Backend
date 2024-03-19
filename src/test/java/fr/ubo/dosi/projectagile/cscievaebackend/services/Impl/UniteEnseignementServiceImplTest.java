package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.UniteEnseignement;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.FormationRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UniteEnseignementServiceImplTest {
    @InjectMocks
    private UniteEnseignementServiceImpl uniteEnseignementService;
    @Mock
    private EvaluationRepository er;

    @Mock
    private FormationRepository formationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetUeListe() {
        Enseignant enseignant = new Enseignant();
        when(er.findAllByNoEnseignant(enseignant)).thenReturn(Arrays.asList(new Evaluation(), new Evaluation()));
        List<Evaluation> evaluations = uniteEnseignementService.getUeListe(enseignant);
        assertEquals(2, evaluations.size());
    }
    @Test
    public void testGetAllUE() {
        List<UniteEnseignement> mockUEs = Arrays.asList(
                new UniteEnseignement(),
                new UniteEnseignement()
        );
        when(er.findAllOrderByCodeUe()).thenReturn(mockUEs);
        List<UniteEnseignement> ues = uniteEnseignementService.getAllUE();
        assertEquals(mockUEs.size(), ues.size());
    }
    @Test
    public void testGetAllUEByPromotions() {
        String codeFormation = "exampleCode";
        Formation formation = new Formation();
        formation.setUniteEnseignements(new HashSet<>());
        when(formationRepository.findById(codeFormation)).thenReturn(Optional.of(formation));
        Set<UniteEnseignement> ues = uniteEnseignementService.getAllUEByPromotions(codeFormation);
        assertNotNull(ues);
    }
}