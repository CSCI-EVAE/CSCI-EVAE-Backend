package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import static org.junit.jupiter.api.Assertions.*;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
@ExtendWith(MockitoExtension.class)
class EvaluationServiceImplTest {

    @InjectMocks
    private EvaluationServiceImpl evaluationService;

    @Mock
    private EvaluationRepository evaluationRepository;

    @Test
    public void testGetEvaluationById_Success() {
        /*Integer id = 1;
        Evaluation evaluation = new Evaluation();
        evaluation.setId(id);

        when(evaluationRepository.findById(id.longValue())).thenReturn(Optional.of(evaluation));

        Evaluation result = evaluationService.getEvaluationById(id.longValue());

        assertEquals(id, result.getId());*/
    }
    @Test
    public void testGetEvaluationById_NotFound() {
         /*  Long id = 1L;
           when(evaluationRepository.findById(id)).thenReturn(Optional.empty());
           Executable executable = () -> evaluationService.getEvaluationById(id);
           assertThrows(NoSuchElementException.class, executable);
 */   }

}
