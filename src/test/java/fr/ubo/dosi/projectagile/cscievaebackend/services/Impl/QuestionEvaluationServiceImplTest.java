package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.QuestionEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionEvaluationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuestionEvaluationServiceImplTest {
    @Mock
    private QuestionEvaluationRepository questionEvaluationRepository;

    @InjectMocks
    private QuestionEvaluationServiceImpl questionEvaluationService;

    @Test
    public void testSaveQuestionEvaluation() {
        QuestionEvaluation questionEvaluation = new QuestionEvaluation();
        when(questionEvaluationRepository.save(questionEvaluation)).thenReturn(questionEvaluation);
        QuestionEvaluation result = questionEvaluationService.saveQuestionEvaluation(questionEvaluation);
        assertNotNull(result);
        assertEquals(questionEvaluation, result);
        verify(questionEvaluationRepository).save(questionEvaluation);
    }

    @Test
    public void testDeleteQuestionEvaluation() {
        QuestionEvaluation questionEvaluation = new QuestionEvaluation();
        questionEvaluationService.deleteQuestionEvaluation(questionEvaluation);
        verify(questionEvaluationRepository).delete(questionEvaluation);
    }
}
