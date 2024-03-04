package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void testCreateQuestion() {
        /*Question question = new Question();
        question.setId(1);
        question.setType("TypeTest");
        question.setIntitule("IntituleTest");

        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(1);
        question.setIdQualificatif(qualificatif);

        Enseignant enseignant = new Enseignant();
        enseignant.setId((short) 1);
        question.setNoEnseignant(enseignant);

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question savedQuestion = questionService.createQuestion(question);

        assertNotNull(savedQuestion);
        assertEquals(1L, (long) savedQuestion.getId()); // Conversion de Optional<Long> à long pour la comparaison
        assertEquals("TypeTest", savedQuestion.getType());
        assertEquals("IntituleTest", savedQuestion.getIntitule());
        assertEquals(qualificatif.getId(), savedQuestion.getIdQualificatif().getId()); // Comparaison des identifiants des qualificatifs
        assertEquals(enseignant.getId(), savedQuestion.getNoEnseignant().getId()); // Comparaison des identifiants des enseignants*/
    }


    @Test
    public void testGetAllQuestions() {
        /*List<Question> expectedQuestions = new ArrayList<>();

        when(questionRepository.findAll()).thenReturn(expectedQuestions);

        List<Question> result = questionService.getAllQuestions();

        assertEquals(expectedQuestions.size(), result.size());*/
    }

    @Test
    public void testGetQuestionById_Success() throws ResourceNotFoundException {
      /*  Integer id = 1;
        Question question = new Question();
        question.setId(id);

        when(questionRepository.findById(id.longValue())).thenReturn(Optional.of(question));

        Question result = questionService.getQuestionById(id.longValue());

        assertEquals(id, result.getId());*/
    }

    @Test
    public void testGetQuestionById_NotFound() {
        /*Long id = 1L;

        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionService.getQuestionById(id));
*/    }

    @Test
    public void testUpdateQuestion_Success() throws ResourceNotFoundException {
       /* Integer id = 1;
        Question existingQuestion = new Question();
        existingQuestion.setId(id);
        existingQuestion.setType("ExistingType");
        existingQuestion.setIntitule("ExistingIntitule");
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(1);
        existingQuestion.setIdQualificatif(qualificatif);
        Enseignant enseignant = new Enseignant();
        enseignant.setId((short) 1);
        existingQuestion.setNoEnseignant(enseignant);*/

        /*Question modifiedQuestion = new Question();
        modifiedQuestion.setType("ModifiedType");
        modifiedQuestion.setIntitule("ModifiedIntitule");
        Qualificatif qualificatif2 = new Qualificatif();
        qualificatif2.setId(2);
        modifiedQuestion.setIdQualificatif(qualificatif2);
        Enseignant enseignant2 = new Enseignant();
        enseignant2.setId((short) 2);
        modifiedQuestion.setNoEnseignant(enseignant2);

        when(questionRepository.findById(id.longValue())).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Question updatedQuestion = questionService.updateQuestion(id.longValue(), modifiedQuestion);

        assertNotNull(updatedQuestion);
        assertEquals(id, updatedQuestion.getId());
        assertEquals("ModifiedType", updatedQuestion.getType());
        assertEquals("ModifiedIntitule", updatedQuestion.getIntitule());*/

    }

    @Test
    public void testUpdateQuestion_NotFound() {
      /*  Long id = 1L;
        when(questionRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> questionService.updateQuestion(id, new Question()));
   */ }

    @Test
    public void testDeleteQuestion_Success() throws ResourceNotFoundException {
        Integer id = 1;
        Question existingQuestion = new Question();
        existingQuestion.setId(id);

        when(questionRepository.findById(id.longValue())).thenReturn(Optional.of(existingQuestion));
        doNothing().when(questionRepository).deleteById(id.longValue());

        assertDoesNotThrow(() -> questionService.deleteQuestion(id.longValue()));
    }

    @Test
    public void testDeleteQuestion_NotFound() {
        Long id = 1L;

        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> questionService.deleteQuestion(id));
    }
}
