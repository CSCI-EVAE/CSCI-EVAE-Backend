package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @Test
    public void testCreateQuestion() {
        Question questionToAdd = new Question();
        when(questionService.createQuestion(any(Question.class))).thenReturn(questionToAdd);
        ResponseEntity<ApiResponse<Question>> response = questionController.createQuestion(questionToAdd);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ApiResponse.ok(questionToAdd), response.getBody());
        verify(questionService, times(1)).createQuestion(questionToAdd);
    }

    @Test
    public void testGetAllQuestions() {
        /*List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        when(questionService.getAllQuestions()).thenReturn(questions);
        ResponseEntity<ApiResponse<List<Question>>> response = questionController.getAllQuestions();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(questions), response.getBody());
        verify(questionService, times(1)).getAllQuestions();*/
    }
      @Test
      public void testGetQuestionById() throws ResourceNotFoundException {
          Integer id = 1;
          Question question = new Question();
          question.setId(id);
          when(questionService.getQuestionById(id.longValue())).thenReturn(question);
          ResponseEntity<ApiResponse<Question>> response = questionController.getQuestionById(id.longValue());
          assertEquals(HttpStatus.OK, response.getStatusCode());
          assertEquals(ApiResponse.ok(question), response.getBody());
          verify(questionService, times(1)).getQuestionById(id.longValue());
      }


    @Test
    public void testGetQuestionById_NotFound() throws ResourceNotFoundException {
        Long id = 1L;
        when(questionService.getQuestionById(id)).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<ApiResponse<Question>> response = questionController.getQuestionById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponse.error("Question not found", null), response.getBody());
        verify(questionService, times(1)).getQuestionById(id);
    }

    @Test
    public void testUpdateQuestion() throws ResourceNotFoundException {
        Long id = 1L;
        Question questionToUpdate = new Question();
        when(questionService.updateQuestion(eq(id), any(Question.class))).thenReturn(questionToUpdate);
        ResponseEntity<ApiResponse<Question>> response = questionController.updateQuestion(id, questionToUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(questionToUpdate), response.getBody());
        verify(questionService, times(1)).updateQuestion(id, questionToUpdate);
    }

    @Test
    public void testUpdateQuestion_NotFound() throws ResourceNotFoundException {
        Long id = 1L;
        Question questionToUpdate = new Question();
        when(questionService.updateQuestion(eq(id), any(Question.class))).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<ApiResponse<Question>> response = questionController.updateQuestion(id, questionToUpdate);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponse.error("Question not found", null), response.getBody());
        verify(questionService, times(1)).updateQuestion(id, questionToUpdate);
    }

    @Test
    public void testDeleteQuestion() throws ResourceNotFoundException {
        Long id = 1L;
        ResponseEntity<ApiResponse<Void>> response = questionController.deleteQuestion(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponse.ok(null), response.getBody());
        verify(questionService, times(1)).deleteQuestion(id);
    }

    @Test
    public void testDeleteQuestion_NotFound() throws ResourceNotFoundException {
        Long id = 1L;
        doThrow(ResourceNotFoundException.class).when(questionService).deleteQuestion(id);
        ResponseEntity<ApiResponse<Void>> response = questionController.deleteQuestion(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ApiResponse.error("Question not found", null), response.getBody());
        verify(questionService, times(1)).deleteQuestion(id);
    }
}
